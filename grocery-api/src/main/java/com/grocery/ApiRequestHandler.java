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

    public void setQueryParam(Map<String, String> queryParam) {
        this.queryParams.putAll(queryParam);
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }
    public void setQueryParam(String key, String value) {
        this.queryParams.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public Map<String, String> getQueryParams() {
        return this.queryParams;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setEndpoint(String endpoint, int id) {
        this.endpoint = endpoint + "/" + id;
    }

    public String getBodyRequest() {
        return bodyRequest;
    }
    public void setBodyRequest(String bodyRequest) {
        this.bodyRequest = bodyRequest;
    }
    public Map<String, ?> getPathParams() {
        return this.pathParams;
    }
    public void setPathParams(Map<String, String> pathParams) {
        this.pathParams.putAll(pathParams);
    }

    public void setPathParams(String key, String value) {
        this.pathParams.put(key, value);
    }
}

