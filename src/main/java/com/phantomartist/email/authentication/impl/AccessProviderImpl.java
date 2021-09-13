package com.phantomartist.email.authentication.impl;

import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import com.phantomartist.email.authentication.AccessProvider;

import okhttp3.Request;

public class AccessProviderImpl implements AccessProvider {

    private GraphServiceClient<Request> serviceClient;
    private TokenCredentialAuthProvider tokenCredentialAuthProvider;

    public AccessProviderImpl(
        final GraphServiceClient<Request> serviceClient,
        final TokenCredentialAuthProvider tokenCredentialAuthProvider) {

        this.serviceClient = serviceClient;
        this.tokenCredentialAuthProvider = tokenCredentialAuthProvider;
    }

    @Override
    public GraphServiceClient<Request> getServiceClient() {
        return serviceClient;
    }

    @Override
    public String getAccessToken() {
        //TODO Implement me correctly
        return null;
    }
}
