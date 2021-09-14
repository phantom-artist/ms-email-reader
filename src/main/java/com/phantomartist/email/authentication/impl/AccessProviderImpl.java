package com.phantomartist.email.authentication.impl;

import java.net.URL;

import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.core.BaseClient;
import com.microsoft.graph.requests.GraphServiceClient;
import com.phantomartist.email.Logger;
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
        try {
            final URL meUrl = new URL(BaseClient.DEFAULT_GRAPH_ENDPOINT + "/me");
            return tokenCredentialAuthProvider.getAuthorizationTokenAsync(meUrl).get();
        } catch (Exception e) {
            Logger.logError("Unable to get authorization access token", e);
            return null;
        }
    }
}
