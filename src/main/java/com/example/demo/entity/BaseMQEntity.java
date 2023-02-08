package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 基础的mq消息实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseMQEntity {


    private String key;

    private String context;

    private String topic;

}
