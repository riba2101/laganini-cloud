package org.laganini.cloud.common.util;

import java.util.Random;

public final class StringUtils {

    private static final Random RANDOM = new Random();

    public static String generateRandomString(int length) {
        return RANDOM
                .ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

}
