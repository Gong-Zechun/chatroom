package top.javahai.chatroom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author Hai
 * @date 2020/6/16 - 23:31
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  /**
   * 注册 WebSocket 端点
   * <p>添加一个 WebSocket 端点 /ws/chat，并允许所有来源的连接，使用 SockJS 作为回退。</p>
   *
   * @param registry
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws/chat").setAllowedOrigins("*").withSockJS();
  }

  /**
   * 配置消息代理
   *
   * @param registry
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    // 启用简单代理，用于广播消息到 /topic 前缀的目的地。
    registry.enableSimpleBroker("/topic");
    // 设置应用消息的前缀为 /app，用于点对点消息。（暂不开通单聊，目前只有群聊）
    registry.setApplicationDestinationPrefixes("/app");
  }
}
