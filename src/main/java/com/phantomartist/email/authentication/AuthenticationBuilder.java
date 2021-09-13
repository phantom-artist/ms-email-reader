package com.phantomartist.email.authentication;

import java.util.Arrays;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import com.phantomartist.email.authentication.impl.AccessProviderImpl;

import okhttp3.Request;

public class AuthenticationBuilder {

    public static AccessProvider createAccessProvider(final String tenantId, final String clientId, final String clientSecret) {

        final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .tenantId(tenantId)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();

        final TokenCredentialAuthProvider tokenCredentialAuthProvider = 
                new TokenCredentialAuthProvider(
                        Arrays.asList("Mail.Read"), 
                        clientSecretCredential);

        final GraphServiceClient<Request> graphClient =
                GraphServiceClient
                  .builder()
                  .authenticationProvider(tokenCredentialAuthProvider)
                  .buildClient();


        return new AccessProviderImpl(graphClient, tokenCredentialAuthProvider);
    }

    private AuthenticationBuilder() {
    }
}
