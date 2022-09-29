package org.laganini.cloud.exception.detail.code.registry;

import lombok.Getter;

import javax.validation.constraints.*;

public enum BeanValidationCode {

    ASSERT_FALSE(AssertFalse.class.getSimpleName()),
    ASSERT_TRUE(AssertTrue.class.getSimpleName()),
    DECIMAL_MAX(DecimalMax.class.getSimpleName()),
    DECIMAL_MIN(DecimalMin.class.getSimpleName()),
    DIGITS(Digits.class.getSimpleName()),
    EMAIL(Email.class.getSimpleName()),
    FUTURE(Future.class.getSimpleName()),
    FUTURE_OR_PRESENT(FutureOrPresent.class.getSimpleName()),
    MAX(Max.class.getSimpleName()),
    MIN(Min.class.getSimpleName()),
    NEGATIVE(Negative.class.getSimpleName()),
    NEGATIVE_OR_ZERO(NegativeOrZero.class.getSimpleName()),
    NOT_BLANK(NotBlank.class.getSimpleName()),
    NOT_EMPTY(NotEmpty.class.getSimpleName()),
    NOT_NULL(NotNull.class.getSimpleName()),
    NULL(Null.class.getSimpleName()),
    PAST(Past.class.getSimpleName()),
    PAST_OR_PRESENT(PastOrPresent.class.getSimpleName()),
    PATTERN(Pattern.class.getSimpleName()),
    POSITIVE(Positive.class.getSimpleName()),
    POSITIVE_OR_ZERO(PositiveOrZero.class.getSimpleName()),
    SIZE(Size.class.getSimpleName()),
    ;

    @Getter
    private final String value;

    BeanValidationCode(String value) {
        this.value = value;
    }

}
