package org.laganini.cloud.rmi.connector.binary;

import lombok.ToString;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@ToString
public class ReactiveFilePart
        implements FilePart {

    private static final DataBufferFactory bufferFactory = new DefaultDataBufferFactory();

    private static final OpenOption[] FILE_CHANNEL_OPTIONS = {
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING,
            StandardOpenOption.WRITE
    };

    private final String      name;
    private final HttpHeaders headers;
    private final InputStream inputStream;

    public ReactiveFilePart(String name, MediaType contentType, InputStream inputStream) {
        this(name, getHttpHeaders(contentType), inputStream);
    }

    public ReactiveFilePart(String name, HttpHeaders headers, InputStream inputStream) {
        this.name = name;
        this.headers = headers;
        this.inputStream = inputStream;
    }

    private static HttpHeaders getHttpHeaders(MediaType contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType.toString());
        return headers;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public HttpHeaders headers() {
        return this.headers;
    }

    @Override
    public String filename() {
        return name;
    }

    @Override
    public Mono<Void> transferTo(File dest) {
        return transferTo(Path.of(dest.toURI()));
    }

    @Override
    public Mono<Void> transferTo(Path dest) {
        try (
                ReadableByteChannel input = Channels.newChannel(inputStream);
                FileChannel output = FileChannel.open(dest, FILE_CHANNEL_OPTIONS)
        ) {
            long size = (input instanceof FileChannel ? ((FileChannel) input).size() : Long.MAX_VALUE);
            long totalWritten = 0;
            while (totalWritten < size) {
                long written = output.transferFrom(input, totalWritten, size - totalWritten);
                if (written <= 0) {
                    break;
                }
                totalWritten += written;
            }
        } catch (IOException ex) {
            return Mono.error(ex);
        }
        return Mono.empty();
    }

    @Override
    public Flux<DataBuffer> content() {
        return DataBufferUtils.readInputStream(() -> inputStream, bufferFactory, 4096);
    }

}
