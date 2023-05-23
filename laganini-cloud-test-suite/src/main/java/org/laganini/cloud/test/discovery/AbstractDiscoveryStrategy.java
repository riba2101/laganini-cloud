package org.laganini.cloud.test.discovery;

import lombok.Getter;

import java.util.function.Predicate;

public abstract class AbstractDiscoveryStrategy implements DiscoveryStrategy {

    protected abstract String getProperty();

    protected abstract String getSuffix();

    @Override
    public boolean applies(Class<?> testClazz) {
        String testClassName = testClazz.getCanonicalName();

        return new DiscoveryTestPredicate(getProperty(), name -> name.endsWith(getSuffix())).test(testClassName);
    }

    @Getter
    protected static class DiscoveryTestPredicate implements Predicate<String> {

        private final String            key;
        private final Predicate<String> predicate;

        DiscoveryTestPredicate(String key, Predicate<String> predicate) {
            this.key = key;
            this.predicate = predicate;
        }

        @Override
        public boolean test(String name) {
            return !Boolean.parseBoolean(System.getProperty(key)) && predicate.test(name);
        }

    }

}
