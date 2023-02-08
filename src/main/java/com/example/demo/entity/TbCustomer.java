package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *劳动者实体类
 */
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_customer") // 和数据库表名对应
@Entity // 声明当前类代表一张数据表
@Data // 生成get set toString 方法
public class TbCustomer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name ="email")
    private String email;

    /**
     * 有三种类型
     * DATE, 精确到天 = 2022-07-30
     * TIME, 当前事件 = 21:20:01
     * TIMESTAMP; 时间戳 = 2022-07-30 21:20:02
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(value =TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Temporal(value =TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;


    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(value =TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @Transient
    private String customerInfo;



    public String getCustomerInfo(){
        return customerName+id+email;
    }


    @Override
    public String toString() {
        return "TbCustomer{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                ", updateTime=" + updateTime +
                ", birthday=" + birthday +
                ", createTime=" + createTime +
                ", customerInfo='" + customerInfo + '\'' +
                '}';
    }
}
