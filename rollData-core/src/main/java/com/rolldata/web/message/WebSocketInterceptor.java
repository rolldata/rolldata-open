package com.rolldata.web.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Title: WebSocketInterceptor
 * @Description: WebSocketInterceptor
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2021-11-15
 * @version: V1.0
 */
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {
    private Logger logger = LogManager.getLogger(WebSocketInterceptor.class);
    /**
     * 在处理握手之前调用，这里是把获取的请求数据绑定到session的map对象中（attributes）
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        // 将ServerHttpRequest转成ServletServerHttpRequest
        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;

        // 获取请求参数，先获取HttpServletRequest对象才能获取请求参数
        HttpServletRequest httpServletrequest = servletServerHttpRequest.getServletRequest();
        logger.info(String.format("Websocket Handshake Interceptor， sessionID：%s",
                httpServletrequest.getSession().getId()));

        // 获取请求的数据
        String clientID = httpServletrequest.getParameter(WebSocketConstant.CLIENT_ID);
        if (null == clientID || "".equals(clientID)) {
            clientID = "system";
//            return false;
        }

        // 把获取的请求数据绑定到session的map对象中（attributes）
        attributes.put(WebSocketConstant.CLIENT_ID, clientID);
        return true;
    }

    /*
     * 握手完成后调用。响应状态和头指示握手的结果，即握手是否成功。
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // TODO Auto-generated method stub
    }

}
