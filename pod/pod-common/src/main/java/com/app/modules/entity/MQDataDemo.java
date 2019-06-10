package com.app.modules.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MQDataDemo {

    //消息体内容
    private String message;

    //消息发送时间
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
    private Date date;


}
