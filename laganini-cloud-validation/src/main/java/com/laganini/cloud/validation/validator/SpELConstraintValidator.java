package com.laganini.cloud.validation.validator;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SpELConstraintValidator implements ConstraintValidator<SpEL, Object> {

    private String expression;
    private ExpressionParser parser = new SpelExpressionParser();

    public void initialize(SpEL constraintAnnotation) {
        expression = constraintAnnotation.value();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        StandardEvaluationContext spelContext = new StandardEvaluationContext(value);
        try {
            return (Boolean) parser.parseExpression(expression).getValue(spelContext);
        } catch (Exception e) {
            return false;
        }
    }

}
