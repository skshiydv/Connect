package io.github.skshiydv.connect.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String senderId;
    private String recipientId;
    private String content;
}
