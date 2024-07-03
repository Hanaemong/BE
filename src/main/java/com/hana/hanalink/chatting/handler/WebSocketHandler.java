//package com.hana.hanalink.chatting.handler;
//
//import com.hana.hanalink.chatting.dto.Message;
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONObject;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Slf4j
//public class WebSocketHandler extends TextWebSocketHandler {
//
//    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
//
//    @Override // 웹 소켓 연결시
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        sessions.put(session.getId(), session);
//
//        Message message = Message.builder().sender(session.getId()).channelId("all").build();
//        message.newConnect();
//
//        for (WebSocketSession s : sessions.values()) {
//            if(!s.getId().equals(session.getId())) {
//                s.sendMessage(new TextMessage("Hi " + new JSONObject(message) + "!"));
//            }
//        }
//    }
//
//    @Override // 메시지 전달
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        JSONObject jsonObject = new JSONObject(payload);
//        for (WebSocketSession s : sessions.values()) {
//            s.sendMessage(new TextMessage(jsonObject.getString("data")));
//        }
//    }
//
//    @Override // 웹소켓 통신 에러시
//    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        super.handleTransportError(session, exception);
//    }
//
//    @Override // 웹 소켓 연결 종료시
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        var sessionId = session.getId();
//
//        sessions.remove(sessionId);
//
//        final Message message = new Message();
//        message.closeConnect();
//        message.setSender(sessionId);
//
//        for (WebSocketSession s : sessions.values()) {
//            s.sendMessage(new TextMessage("BYE " + new JSONObject(message) + "!"));
//        }
//    }
//}
