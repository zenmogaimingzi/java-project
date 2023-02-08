package com.example.demo.mq;



import com.example.demo.emun.MessageMatchEnum;
import com.example.demo.entity.TbCustomer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//生产者
@RestController
public class SendController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate; //模板类：建立连接以及断开连接

    @RequestMapping("/send")
    public String send() {
//        //发送逻辑
        String msg = "hello world!";
//        //convert自动把消息转换为底层的字节数组，无需人为转换
//        rocketMQTemplate.convertAndSend("topic10", msg);
        TbCustomer user = new TbCustomer();
        user.setCustomerName("张三");
        user.setCreateTime(new Date());
        user.setId(12L);
        user.setEmail("123@qq.com");
        user.setBirthday(new Date());
        rocketMQTemplate.convertAndSend("topic10", user);
        //同步消息
        SendResult sendResult = rocketMQTemplate.syncSend("topic10", user);
        System.out.println(sendResult);
        //异步消息
        rocketMQTemplate.asyncSend("topic10", user, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println(throwable);
            }
        }, 1000);
        //单向消息
        rocketMQTemplate.sendOneWay("topic10", user);

        rocketMQTemplate.sendOneWay("topic10", user);


        //延时消息
        rocketMQTemplate.syncSend("topic9", MessageBuilder.withPayload(msg).build(), 2000, 3);

        //批量消息
        int i = 0;
        String msg1 = ("这是第" + i++ + "条消息");
        Message message1 = new Message("topic3", "tag1", msg1.getBytes());
        String msg2 = ("这是第" + i++ + "条消息");
        Message message2 = new Message("topic3", "tag1", msg2.getBytes());
        String msg3 = ("这是第" + i++ + "条消息");
        Message message3 = new Message("topic3", "tag1", msg3.getBytes());
        String msg4 = ("这是第" + i++ + "条消息");
        Message message4 = new Message("topic3", "tag1", msg4.getBytes());
        String msg5 = ("这是第" + i++ + "条消息");
        Message message5 = new Message("topic3", "tag1", msg5.getBytes());
        List<Message> msglist = new ArrayList<>();
        msglist.add(message1);
        msglist.add(message2);
        msglist.add(message3);
        msglist.add(message4);
        msglist.add(message5);
        rocketMQTemplate.syncSend("topic10", msglist, 1000);
        return "success!";

    }

    @Autowired
    private MessageProducer messageProducer;



    @RequestMapping("/sendV2")
    public String sendV2() {


        String mess = "message20200";
        messageProducer.sendMessage(MessageMatchEnum.DELAY_MESSAGE.getKey(),mess);
        return "success!";

    }
}
