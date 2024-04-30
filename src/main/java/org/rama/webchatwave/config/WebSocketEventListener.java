package org.rama.webchatwave.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rama.webchatwave.model.Chat;
import org.rama.webchatwave.model.MessageType;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageSendingOperations;

    @EventListener
    public void handleDisconnectListener(SessionDisconnectEvent disconnectEvent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(disconnectEvent.getMessage());
        String username = (String) accessor.getSessionAttributes().get("username");

        if (username != null) {
            log.info("user disconnected:{}", username);
            Chat message = Chat.builder().type(MessageType.LEAVE).sender(username).build();
            messageSendingOperations.convertAndSend("/topic/public", message);
        }
    }
}
