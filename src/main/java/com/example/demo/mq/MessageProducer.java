package com.example.demo.mq;

import com.example.demo.entity.BaseMQEntity;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author ***
 *
 * 消息发送
 */
@Component
public class MessageProducer {


    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    public void sendMessage(String topicKey , String mess){


        BaseMQEntity message = new BaseMQEntity(topicKey,mess, "all_message_topic");

        rocketMQTemplate.syncSend("all_message_topic", message);
    }


}
