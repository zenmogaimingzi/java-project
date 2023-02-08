package com.example.demo.emun;


public enum MessageMatchEnum {

    /**
     * 延迟消息
     */
    DELAY_MESSAGE("DELAY_MESSAGE","delayMessage")
    ;


    MessageMatchEnum(String key, String method) {
        this.key = key;
        this.method = method;
    }


    /**
     * 使用的topic中的key
     */
    private String key;


    /**
     * mq所对应的method
     */
    private String method;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    /**
     *
     * 过去消息类型
     * @param key
     * @return
     */
    public static MessageMatchEnum getMessageType(String key){
        for (MessageMatchEnum matchEnum : MessageMatchEnum.values()) {
            if (matchEnum.getKey().equals(key)) {
                return matchEnum;
            }
        }
        return null;
    }



}