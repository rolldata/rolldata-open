package com.rolldata.web.message;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Title: WebSocketConfig
 * @Description: WebSocketConfig
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2021-11-15
 * @version: V1.0
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MessageHandler(), "websocket")
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOrigins("*"); //允许跨域访问
        registry.addHandler(new MessageHandler(),"/sockjs")
                .addInterceptors(new WebSocketInterceptor()).setAllowedOrigins("*").withSockJS();
    }
}
