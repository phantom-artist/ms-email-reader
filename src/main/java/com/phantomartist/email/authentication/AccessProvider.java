package com.phantomartist.email.authentication;

import com.microsoft.graph.requests.GraphServiceClient;

import okhttp3.Request;

public interface AccessProvider {

    public GraphServiceClient<Request> getServiceClient();

    public String getAccessToken();
}
