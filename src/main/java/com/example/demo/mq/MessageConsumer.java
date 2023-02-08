package com.example.demo.mq;

import com.example.demo.entity.BaseMQEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author ****
 * <p>
 * 消息消费者
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "all_message_topic",
        consumerGroup = "message_consumer",
        selectorExpression = "*",
        messageModel = MessageModel.BROADCASTING)
public class MessageConsumer implements RocketMQListener<BaseMQEntity> {

    @Override
    public void onMessage(BaseMQEntity object) {

        log.info(""+object);

        System.out.println("end");

    }


}
