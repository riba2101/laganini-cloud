package org.laganini.cloud.test.discovery;

import org.junit.jupiter.engine.descriptor.ClassBasedTestDescriptor;
import org.junit.jupiter.engine.descriptor.ClassTestDescriptor;
import org.junit.jupiter.engine.descriptor.MethodBasedTestDescriptor;
import org.junit.platform.engine.FilterResult;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.launcher.PostDiscoveryFilter;

import java.util.Optional;

public class TestPostDiscoveryFilter implements PostDiscoveryFilter {

    @Override
    public FilterResult apply(TestDescriptor testDescriptor) {
        if (ClassTestDescriptor.class.isAssignableFrom(testDescriptor.getClass())) {
            return decideBasedOnClass((ClassTestDescriptor) testDescriptor);
        }

        if (ClassBasedTestDescriptor.class.isAssignableFrom(testDescriptor.getClass())) {
            return apply(getParentUntil(testDescriptor));
        }

        if (MethodBasedTestDescriptor.class.isAssignableFrom(testDescriptor.getClass())) {
            return apply(getParentUntil(testDescriptor));
        }

        return FilterResult.included("Not processed by LaganiniPostDiscoveryFilter");
    }

    private FilterResult decideBasedOnClass(ClassTestDescriptor testDescriptor) {
        return decideBasedOnClassName(testDescriptor.getTestClass());
    }

    private TestDescriptor getParentUntil(TestDescriptor testDescriptor) {
        if (ClassTestDescriptor.class.isAssignableFrom(testDescriptor.getClass())) {
            return testDescriptor;
        }

        Optional<TestDescriptor> potentialParent = testDescriptor.getParent();
        if (potentialParent.isEmpty()) {
            throw new IllegalStateException("Parent in nested class empty");
        }

        return getParentUntil(potentialParent.get());
    }

    private FilterResult decideBasedOnClassName(Class<?> testClazz) {
        String testClassName = testClazz.getCanonicalName();

        return FilterResult.includedIf(
                shouldRun(testClazz),
                () -> testClassName + " included, was matched",
                () -> testClassName + " excluded, did not match any filter"
        );
    }

    private boolean shouldRun(Class<?> testClazz) {
        return TestDiscoveryRegistry
                .getAll()
                .stream()
                .anyMatch(strategy -> strategy.applies(testClazz));
    }

}
