package com.laganini.cloud.rmi.client;

import brave.baggage.BaggageField;
import brave.http.HttpRequestParser;
import com.laganini.cloud.common.SpanConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.info.BuildProperties;
import org.springframework.cloud.sleuth.instrument.web.HttpServerRequestParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration(proxyBeanMethods = false)
@EnableOpenApi
public class LaganiniSwaggerAutoConfiguration {

    public static final String SPAN_ACCOUNT_ID_HEADER = "X-Swagger-" + SpanConstants.ACCOUNT_ID_KEY;

    @Bean
    @ConditionalOnMissingBean
    protected Docket api(BuildProperties buildProperties) {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo(buildProperties))
                .globalRequestParameters(Collections.singletonList(buildSpanParameter()))
                .select()
                .paths(PathSelectors.any())
                .build();
    }

    protected ApiInfo apiInfo(BuildProperties buildProperties) {
        return new ApiInfoBuilder()
                .title(buildProperties.getArtifact())
                .version(buildProperties.getVersion())
                .build();
    }

    protected RequestParameter buildSpanParameter() {
        return new RequestParameterBuilder()
                .name(SPAN_ACCOUNT_ID_HEADER)
                .in(ParameterType.HEADER)
                .build();
    }

    @Bean(name = HttpServerRequestParser.NAME)
    @ConditionalOnMissingBean
    HttpRequestParser myHttpRequestParser() {
        return (request, context, span) -> {
            String accountId = request.header(SPAN_ACCOUNT_ID_HEADER);
            if (accountId != null) {
                BaggageField baggageField = BaggageField.create(SpanConstants.ACCOUNT_ID_KEY);
                baggageField.updateValue(context, accountId);
            }
        };
    }

}
