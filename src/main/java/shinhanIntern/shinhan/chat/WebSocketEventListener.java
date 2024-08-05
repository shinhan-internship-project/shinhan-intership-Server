package shinhanIntern.shinhan.chat;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class WebSocketEventListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ConcurrentMap<String, Integer> roomSubscriptions = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, String> sessionRoomMap = new ConcurrentHashMap<>();

    public WebSocketEventListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String destination = headerAccessor.getDestination();

        if (destination != null && destination.startsWith("/sub/room/")) {
            String roomId = destination.substring("/sub/room/".length());
            sessionRoomMap.put(sessionId, roomId);
            roomSubscriptions.merge(roomId, 1, Integer::sum);
            updateRoomUserCount(roomId);
        }
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String roomId = sessionRoomMap.remove(sessionId);

        if (roomId != null) {
            roomSubscriptions.merge(roomId, -1, Integer::sum);
            updateRoomUserCount(roomId);
        }
    }

    private void updateRoomUserCount(String roomId) {
        int userCount = roomSubscriptions.getOrDefault(roomId, 0);
        messagingTemplate.convertAndSend("/sub/room/" + roomId + "/userCount", userCount);
    }

    public int getUserCount(String roomId) {
        return roomSubscriptions.getOrDefault(roomId, 0);
    }
}