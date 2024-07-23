package io.github.skshiydv.connect.Services;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import io.github.skshiydv.connect.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatService {
    private final SocketIOServer server;
    private final ConcurrentHashMap<String, SocketIOClient> clients = new ConcurrentHashMap<>();
    @Autowired
    public ChatService(SocketIOServer server) {
        this.server = server;
        server.addConnectListener(this::onConnect);
        server.addDisconnectListener(this::onDisconnect);
        server.addEventListener("message", ChatMessage.class, (client, message, ackRequest) -> onMessage(client, message));
    }
    @OnConnect
    private void onConnect(SocketIOClient socketIOClient) {
        String userId=socketIOClient.getHandshakeData().getSingleUrlParam("userId");
        clients.put(userId,socketIOClient);
        System.out.println("user connected"+userId);
    }
    @OnDisconnect
    public void onDisconnect(SocketIOClient socketIOClient) {
        String userId=socketIOClient.getHandshakeData().getSingleUrlParam("userId");
        clients.remove(userId);
        System.out.println("user disconnected"+userId);
    }
    @OnEvent("message")
    public void onMessage(SocketIOClient client, ChatMessage message) {
        SocketIOClient recipientClient = clients.get(message.getRecipientId());
        if (recipientClient != null) {
            recipientClient.sendEvent("message", message);
        }
    }


}
