package org.laganini.cloud.test.performance;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.Getter;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.apache.commons.math3.stat.descriptive.rank.Min;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GatlingReportParser {

    public static Results process(Path simulationLog, GatlingSimulationFormat format) throws Exception {
        Map<String, List<Request>> requests = new HashMap<>();

        CSVParser parser = new CSVParserBuilder()
                .withSeparator('\t')
                .build();

        try (
                CSVReader reader = new CSVReaderBuilder(new FileReader(simulationLog.toFile()))
                        .withCSVParser(parser)
                        .build()
        ) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (!"REQUEST".equals(nextLine[format.typeIndex])) {
                    continue;
                }
                GatlingRequestStatus status = GatlingRequestStatus.valueOf(nextLine[format.statusIndex]);
                String name = nextLine[format.nameIndex];
                long start = Long.parseLong(nextLine[format.requestStartIndex]);
                long end = Long.parseLong(nextLine[format.responseEndIndex]);
                String message = nextLine[format.messageIndex];

                List<Request> list = requests.computeIfAbsent(name, k -> new ArrayList<>());
                Request request = new Request(name, status, start, end, message);
                list.add(request);
            }
        }

        return new Results(calculateStats(requests));
    }

    private static List<Result> calculateStats(Map<String, List<Request>> requests) {
        List<Result> results = new ArrayList<>();

        Min min = new Min();
        Max max = new Max();
        Mean mean = new Mean();
        Percentile percentile = new Percentile();
        StandardDeviation stdDev = new StandardDeviation(false);

        for (Map.Entry<String, List<Request>> entry : requests.entrySet()) {
            List<Request> request = entry.getValue();
            List<Request> ok = new ArrayList<>();
            List<Request> ko = new ArrayList<>();
            double[] times = new double[request.size()];

            for (int i = 0; i < request.size(); i++) {
                Request currentRequest = request.get(i);
                times[i] = currentRequest.endOfResponseReceiving - currentRequest.startOfRequestSending;
                if (currentRequest.status == GatlingRequestStatus.OK) {
                    ok.add(currentRequest);
                }
                if (currentRequest.status == GatlingRequestStatus.KO) {
                    ko.add(currentRequest);
                }
            }

            long currentMin = (long) min.evaluate(times);
            long currentMax = (long) max.evaluate(times);
            long currentMean = (long) mean.evaluate(times);
            percentile.setData(times);
            percentile.setQuantile(95);
            long percentile95 = (long) percentile.evaluate();
            percentile.setQuantile(99);
            long percentile99 = (long) percentile.evaluate();
            long stdDeviation = (long) stdDev.evaluate(times, currentMean);

            Stat stat = new Stat(
                    entry.getKey(),
                    request.size(),
                    ok.size(),
                    ko.size(),
                    currentMin,
                    currentMax,
                    currentMean,
                    stdDeviation,
                    percentile95,
                    percentile99
            );

            results.add(new Result(entry.getKey(), stat, ok, ko));
        }

        return results;
    }

    public enum GatlingSimulationFormat {
        //https://groups.google.com/forum/#!topic/gatling/mbvN5CBDK4w
        //[scenario][userId][recordType][groupHierarchy][name][first/last byte sent timestamp][first/last byte received timestamp][status][extraInfo]
        v1(2, 4, 5, 8, 9, 10),
        // From io.gatling.core.stats.writer
        //fast"${RequestRecordHeader.value}][scenario][userId][{serializeGroups(groupHierarchy)}][name][startTimestamp][endTimestamp][status][{serializeMessage(message)}${serializeExtraInfo(extraInfo)}$
        v2(0, 4, 5, 6, 7, 8),
        v3(0, 2, 3, 4, 5, 6);

        int typeIndex;
        int nameIndex;
        int requestStartIndex;
        int responseEndIndex;
        int statusIndex;
        int messageIndex;

        GatlingSimulationFormat(int typeIndex, int nameIndex, int requestStartIndex, int responseEndIndex, int statusIndex, int messageIndex) {
            this.typeIndex = typeIndex;
            this.nameIndex = nameIndex;
            this.requestStartIndex = requestStartIndex;
            this.responseEndIndex = responseEndIndex;
            this.statusIndex = statusIndex;
            this.messageIndex = messageIndex;
        }
    }

    public enum GatlingRequestStatus {
        OK,
        KO
    }

    @Getter
    public static class Results {
        private final List<Result> results;

        public Results(List<Result> results) {
            this.results = results;
        }

    }

    @Getter
    public static class Result {
        private final String name;
        private final Stat stats;
        private final List<Request> ok;
        private final List<Request> ko;

        public Result(String url, Stat stats, List<Request> ok, List<Request> ko) {
            this.name = url;
            this.stats = stats;
            this.ok = ok;
            this.ko = ko;
        }

        @Override
        public String toString() {
            return "Results{" +
                    "stats=" + stats +
                    ", ok=" + ok.size() +
                    ", ko=" + ko.size() +
                    '}';
        }
    }

    @Getter
    public static class Request {
        private final String name;
        private final GatlingRequestStatus status;
        private final long startOfRequestSending;
        private final long endOfResponseReceiving;
        private final String message;

        public Request(String name, GatlingRequestStatus status, long start, long end, String message) {
            this.name = name;
            this.status = status;
            this.startOfRequestSending = start;
            this.endOfResponseReceiving = end;
            this.message = message;
        }

        public String toString() {
            return name + ": " + endOfResponseReceiving + " - " + startOfRequestSending + " = " + (endOfResponseReceiving - startOfRequestSending);
        }
    }

    @Getter
    public static class Stat {
        private final String name;
        private final long totalCount;
        private final long okCount;
        private final long koCount;
        private final long min;
        private final long max;
        private final long mean;
        private final long stdDeviation;
        private final long percentile95;
        private final long percentile99;

        public Stat(String name, long total, long okCount, long koCount, long min, long max, long mean, long stdDeviation, long percentile95, long percentile99) {
            this.name = name;
            this.totalCount = total;
            this.okCount = okCount;
            this.koCount = koCount;
            this.min = min;
            this.max = max;
            this.mean = mean;
            this.stdDeviation = stdDeviation;
            this.percentile95 = percentile95;
            this.percentile99 = percentile99;
        }

        @Override
        public String toString() {
            return String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", name, totalCount, okCount, koCount, min, max, mean, stdDeviation, percentile95, percentile99);
        }
    }

}
