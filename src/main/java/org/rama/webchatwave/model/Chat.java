package org.rama.webchatwave.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Chat {
    private String message;
    private String sender;
    private MessageType type;
}
