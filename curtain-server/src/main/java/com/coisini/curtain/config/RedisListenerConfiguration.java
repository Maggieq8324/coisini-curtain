package com.coisini.curtain.config;

import com.coisini.curtain.core.listener.TopicMessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

/**
 * @Description Redis 事件广播配置类
 * @author coisini
 * @date Aug 25, 2021
 * @Version 1.0
 */
//@Configuration
public class RedisListenerConfiguration {

    @Value("${spring.redis.listen-pattern}")
    public String pattern;

    @Bean
    public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory redisConnection) {
//        return new TopicMessageListener()
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnection);

        /**
         * Topic是消息发布(Pub)者和订阅(Sub)者之间的传输中介
         */
        Topic topic = new PatternTopic(this.pattern);

        container.addMessageListener(new TopicMessageListener(), topic);
        return container;
    }
}
