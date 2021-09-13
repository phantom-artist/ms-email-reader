package com.phantomartist.email.wrapper;

import java.util.List;

public interface Message {

    String getId();

    boolean hasAttachments();

    List<Attachment> getAttachments();
}
