package com.phantomartist.email;

import java.util.Arrays;
import java.util.List;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.Attachment;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.MessageCollectionPage;

import okhttp3.Request;

public class App {

    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";
    private static final String TENANT = "";

    public static void main(String[] args) {


        final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .tenantId(TENANT)
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

        final MessageCollectionPage messages = graphClient.me().mailFolders("{id}").messages()
                .buildRequest()
                .get();

        messages.getCurrentPage().forEach(msg -> {
            final List<Attachment> attachments = msg.attachments.getCurrentPage();
            attachments.forEach(attachment -> {
                if ("application/zip".contentEquals(attachment.contentType)) {
                    // TODO: Do something
                }
            });
        });
    }
}
