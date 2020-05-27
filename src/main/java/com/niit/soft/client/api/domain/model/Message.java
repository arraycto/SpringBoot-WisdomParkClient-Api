package com.niit.soft.client.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Tao
 * @version 1.0
 * @ClassName Message
 * @Description TODO
 * @date 2020-05-26 14:47
 **/
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_message_id",nullable = false)
    private Long pkMessageId;

    @Column(name = "content",nullable = false)
    private String content	;

    @Column(name = "is_readed",nullable = false)
    private Boolean isReaded;

    @Column(name = "gmt_create",nullable = false)
    private Timestamp gmtCreate;

    @Column(name = "gmt_modified",nullable = false)
    private Timestamp gmtModified;
}
