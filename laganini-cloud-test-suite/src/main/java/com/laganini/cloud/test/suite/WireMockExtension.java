package com.laganini.cloud.test.suite;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class WireMockExtension
        implements BeforeEachCallback, AfterEachCallback, BeforeAllCallback, AfterAllCallback, TestInstancePostProcessor {

    private final WireMockServer stubServer;

    private ObjectMapper objectMapper;

    public WireMockExtension(Options options) {
        this.stubServer = new WireMockServer(options);
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        objectMapper = SpringExtension.getApplicationContext(context).getBean(ObjectMapper.class);
    }

    @Override
    public void afterAll(ExtensionContext context) {
        stubServer.stop();
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        stubServer.start();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        //do nothing
    }

    @Override
    public void afterEach(ExtensionContext context) {
        stubServer.resetAll();
    }

    @SneakyThrows
    public WireMockExtension stubFor(UrlPattern url, Object response, HttpStatus status) {
        String body = objectMapper.writeValueAsString(response);
        stubServer.stubFor(WireMock
                .post(url)
                .willReturn(WireMock
                        .aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(status.value())
                        .withBody(body)
                )
        );

        return this;
    }

    public WireMockExtension stubFor(UrlPattern url, HttpStatus status) {
        stubServer.stubFor(WireMock
                .post(url)
                .willReturn(WireMock
                        .aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(status.value())
                )
        );

        return this;
    }

    public WireMockExtension stubFor(UrlPattern url, Object response) {
        return stubFor(url, response, HttpStatus.OK);
    }

    public WireMockExtension stubFor(Object response) {
        return stubFor(WireMock.anyUrl(), response);
    }

}

