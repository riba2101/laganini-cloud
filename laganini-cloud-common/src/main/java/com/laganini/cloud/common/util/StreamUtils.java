package org.laganini.cloud.common.util;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class StreamUtils {

    @SafeVarargs
    public static <T> Optional<T> firstPresent(Supplier<Optional<T>>... suppliers) {
        return Stream
                .of(suppliers)
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

}
