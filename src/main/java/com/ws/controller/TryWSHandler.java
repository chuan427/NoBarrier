package com.ws.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class TryWSHandler extends TextWebSocketHandler {

	private static final Set<WebSocketSession> connectedSessions = Collections.synchronizedSet(new HashSet<>());

	
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        connectedSessions.add(session);
        System.out.println("有一位使用者建立ws連線!");//確認使用者有連進來
        System.out.println(connectedSessions);//確認使用者有被加入session集合

        String text = String.format("Session ID = %s, connected", session.getId());
        System.out.println(text);
    }

    //處理消息
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
//        ByteBuffer payload = message.getPayload();
        
//        System.out.println(Arrays.toString(payload.array()));
    	System.out.println(message);
//-----------------------------------------------------------

        // 發送回覆給連接的客戶端
        String replyTextForSender = "{got it";
        TextMessage tm = new TextMessage(replyTextForSender);
//        byte[] replyBytesForSender = replyTextForSender.getBytes("UTF-8"); // 文字格式轉成位元格式
//
//        BinaryMessage replyMessageForSender = new BinaryMessage(ByteBuffer.wrap(replyBytesForSender));
        session.sendMessage(tm);
////-----------------------------------------------------------
//        String replyTextForOthers = "someone sent it";
//        byte[] replyBytesForOthers = replyTextForOthers.getBytes("UTF-8"); // 文字格式轉成位元格式
//        
//        BinaryMessage replyMessageForOthers = new BinaryMessage(ByteBuffer.wrap(replyBytesForOthers));
        
        broadcastToAll(message,session);
        
    }

    // 當WebSocket連接關閉時的處理邏輯
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        connectedSessions.remove(session);
        String text = String.format("session ID = %s, disconnected; close code = %d; reason = %s",
                session.getId(), status.getCode(), status.getReason());
        System.out.println(text);
    }

    // 處理傳輸錯誤的邏輯
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.out.println("Error: " + exception.toString());
    }

    // 根據需要添加其他方法...

    // 定義Spring Bean，並返回TogetherWSHandler實例
    @Bean
    public TextWebSocketHandler tryWSHandlerr() {
        return new TryWSHandler();
    }
    
    private void broadcastToAll(TextMessage message, WebSocketSession senderSession) {
        for (WebSocketSession session : connectedSessions) {
            if (session.isOpen() && !session.getId().equals(senderSession.getId())) {
                try {
					session.sendMessage(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
    }
	
}
