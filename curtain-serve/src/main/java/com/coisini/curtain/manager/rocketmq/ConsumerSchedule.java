package com.coisini.curtain.manager.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

/**
 * @Description MQ消费者
 *      CommandLineRunner 初始化预加载数据
 * @author coisini
 * @date Aug 25, 2021
 * @Version 1.0
 */
//@Component
public class ConsumerSchedule implements CommandLineRunner {

    @Value("${rocketmq.consumer.consumer-group}")
    private String consumerGroup;

    @Value("${rocketmq.namesrv-addr}")
    private String nameSrvAddr;

    public void messageListener() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(this.consumerGroup);
        consumer.setNamesrvAddr(this.nameSrvAddr);

        /**
         * 订阅主题
         */
        consumer.subscribe("Topic", "*");

        /**
         * 设置消费消息数
         */
        consumer.setConsumeMessageBatchMaxSize(1);

        /**
         * 注册消息监听
         */
        consumer.registerMessageListener((MessageListenerConcurrently) (messages, context) -> {
            for (Message message : messages) {
                System.out.println("监听到消息：" + new String(message.getBody()));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
    }

    @Override
    public void run(String... args) throws Exception {
        this.messageListener();
    }
}
