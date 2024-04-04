package com.grocery;

import java.util.HashMap;
import java.util.Map;

public class ApiRequestHandler {

    private Map<String, String> headers;

    private Map<String, String> queryParams;

    private Map<String, String> pathParams;

    private String baseUrl;

    private String endpoint;

    private String bodyRequest;

    public ApiRequestHandler() {
        headers = new HashMap<>();
        queryParams = new HashMap<>();
        pathParams = new HashMap<>();
        bodyRequest = new String();
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    public void setQueryParam(Map<String, String> queryParam) {
        this.queryParams.putAll(queryParam);
    }

    public void setPathParams(Map<String, String> pathParams) {
        this.pathParams.putAll(pathParams);
    }

    public void setBodyRequest(String bodyRequest) {
        this.bodyRequest = bodyRequest;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public Map<String, String> getQueryParams() {
        return this.queryParams;
    }

    public Map<String, String> getPathParams() {
        return this.pathParams;
    }

    public String getBodyRequest() {
        return bodyRequest;
    }
}

