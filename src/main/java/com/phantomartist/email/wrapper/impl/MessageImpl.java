package com.phantomartist.email.wrapper.impl;

import java.util.ArrayList;
import java.util.List;

import com.phantomartist.email.authentication.AccessProvider;
import com.phantomartist.email.wrapper.Attachment;
import com.phantomartist.email.wrapper.Message;

public class MessageImpl implements Message {

    private AccessProvider accessProvider;
    private com.microsoft.graph.models.Message msMessage;
    
    public MessageImpl(
        final AccessProvider accessProvider, 
        final com.microsoft.graph.models.Message msMessage) {

        this.accessProvider = accessProvider;
        this.msMessage = msMessage;
    }

    @Override
    public String getId() {
        return msMessage.id;
    }

    @Override
    public boolean hasAttachments() {
        return msMessage.hasAttachments;
    }

    @Override
    public List<Attachment> getAttachments() {

        final List<Attachment> attachments = new ArrayList<>();
        msMessage.attachments.getCurrentPage().forEach(attachment -> {
            attachments.add(new AttachmentImpl(
                accessProvider, 
                msMessage.id, 
                attachment));
        });
        return attachments;
    }
}
