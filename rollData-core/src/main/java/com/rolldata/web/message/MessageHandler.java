package com.rolldata.web.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

/**
 * @Title: MessageHandler
 * @Description: MessageHandler
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2021-11-15
 * @version: V1.0
 */
@Service
public class MessageHandler extends TextWebSocketHandler {
    private static final Logger logger = LogManager.getLogger(MessageHandler.class);
    /**
     * 在线用户列表
     */
    private static final Map<String, List<WebSocketSession>> onlineUsers;

    static {
        onlineUsers = Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        // 获取WebSocket客户端ID
        String clientID = getWebSocketClientID(session);

        List<WebSocketSession> webSocketSessionList = onlineUsers.get(clientID);
        if (null != webSocketSessionList) {
            webSocketSessionList.add(session);
        } else {
            webSocketSessionList = new ArrayList<>();
            webSocketSessionList.add(session);

            onlineUsers.put(clientID, webSocketSessionList);
        }
        recordLog("Websocket connection successful， wsClientID：%s，wsSessionID：%s。",
                clientID, session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {

        // 获取WebSocket客户端ID
        String clientID = getWebSocketClientID(session);

        List<WebSocketSession> webSocketSessionList = onlineUsers.get(clientID);
        if (null != webSocketSessionList) {
            webSocketSessionList.remove(session);
        }
        recordLog("Websocket disconnected successfully， wsClientID：%s，wsSessionID：%s。",
                clientID, session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

        // 获取WebSocket客户端ID
        String clientID = getWebSocketClientID(session);
        recordLog("Websocket received the message successfully， wsClientID：%s，wsSessionID：%s。",
                clientID, session.getId());

        // 将前端接收的消息回发给客户端
        sendMessageToAllUser( message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {

        // 获取WebSocket客户端ID
        String clientID = getWebSocketClientID(session);

        List<WebSocketSession> webSocketSessionList = onlineUsers.get(clientID);
        if (null != webSocketSessionList) {
            webSocketSessionList.remove(session);
        }
        recordLog("Websocket handles connection exceptions， wsClientID：%s，wsSessionID：%s。",
                clientID, session.getId());
    }

    /**
     * 获取WebSocket客户端ID
     * @param session WebSocketSession
     * @return WebSocket客户端ID
     */
    private String getWebSocketClientID(WebSocketSession session) {
        return (String) session.getAttributes().get(WebSocketConstant.CLIENT_ID);
    }

    /**
     * 发送消息
     * @param clientID 客户端ID
     * @param textMessage 消息对象
     */
    private static void sendMessage(String clientID, String sessionID, TextMessage textMessage) {
        // 根据客户端ID查询webSocketSession集合
        List<WebSocketSession> webSocketSessionList = onlineUsers.get(clientID);

        if (null == webSocketSessionList) {
//            recordLog("Not find webSocketSession by clientID， wsClientID：%s。", clientID);
            return;
        }

        for (WebSocketSession webSocketSession : webSocketSessionList) {
            // 得到webSocketSessionID
            String webSocketSessionID = webSocketSession.getId();

            boolean flag = false;
            // 当sessionID为null时，表示给所有根据clientID查询出来的客户端都发送；当sessionID不为null时，表示只给和sessionID相同的客户端单独发送；
            if (null == sessionID || sessionID.equals(webSocketSessionID)) {
                flag = true;
            }

            // 判断连接是否仍然处于连接状态
            if (webSocketSession.isOpen()) {
                if(flag) {
                    try {
                        webSocketSession.sendMessage(textMessage);
                        recordLog("Send webSocket message success， wsClientID：%s，wsSessionID：%s。",
                                clientID, webSocketSessionID);
                    } catch (IOException e) {
                        recordLog(
                                "Send webSocket message exception，wsClientID：%s，wsSessionID：%s，exceptionMessage：%s。",
                                clientID, webSocketSessionID, e.getMessage());
                        e.printStackTrace();
                    }
                }
            } else {
                recordLog(
                        "Send webSocket message faile，client state is disconnected，wsClientID：%s，wsSessionID：%s。",
                        clientID, webSocketSessionID);
            }
        }
    }
    /**
     * 给所有用户发送消息
     * @param message 消息对象
     */
    public static void sendMessageToAllUser(String message) {
        sendMessageToAllUser(new TextMessage(message));
    }
    /**
     * 给所有用户发送消息
     * @param textMessage 消息对象
     */
    public static void sendMessageToAllUser(TextMessage textMessage) {
        //循环所有用户

        Set<String> clientIds = onlineUsers.keySet();
        for (String clientID : clientIds) {
            // 根据客户端ID查询webSocketSession集合
            List<WebSocketSession> webSocketSessionList = onlineUsers.get(clientID);

            if (null == webSocketSessionList) {
//            recordLog("Not find webSocketSession by clientID， wsClientID：%s。", clientID);
                return;
            }

            for (WebSocketSession webSocketSession : webSocketSessionList) {
                // 得到webSocketSessionID
                String webSocketSessionID = webSocketSession.getId();

                // 判断连接是否仍然处于连接状态
                if (webSocketSession.isOpen()) {
                    try {
                        webSocketSession.sendMessage(textMessage);
                        recordLog("Send webSocket message success， wsClientID：%s，wsSessionID：%s，textMsg:%s。",
                                clientID, webSocketSessionID,textMessage);
                    } catch (IOException e) {
                        recordLog(
                                "Send webSocket message exception，wsClientID：%s，wsSessionID：%s，exceptionMessage：%s。",
                                clientID, webSocketSessionID, e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    recordLog(
                            "Send webSocket message faile，client state is disconnected，wsClientID：%s，wsSessionID：%s。",
                            clientID, webSocketSessionID);
                }
            }

        }
    }
    /**
     * 发送信息
     * @param clientID 客户端ID
     * @param message 消息
     */
    public static void sendMessage(String clientID, String message) {
        sendMessage(clientID, null, new TextMessage(message));
    }

    /**
     * 记录日志
     * @param message 日志信息
     * @param args 日志信息中的参数值
     */
    private static void recordLog(String message, Object... args) {
        logger.info(String.format(message, args));
    }

}
