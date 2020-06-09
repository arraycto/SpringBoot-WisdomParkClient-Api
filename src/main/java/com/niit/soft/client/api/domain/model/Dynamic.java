package com.niit.soft.client.api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Yujie_Zhao
 * @ClassName Dynamic
 * @Description 校友圈动态
 * @Date 2020/6/8  13:56
 * @Version 1.0
 **/
@Table(name = "dynamic", indexes = {@Index(name = "titleIndex", columnList = "title")})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Dynamic {

    /**
     * 主键，策略为自增
     */
    @Id
    private Long pkDynamicId;

    /**
     * 标题
     */
    @Column(name = "title", nullable = false, length = 20)
    private String title = "";

    /**
     * 内容
     */
    @Column(name = "content", nullable = false)
    private String content = "";

    /**
     * 点赞数
     */
    @Column(name = "thumbs", nullable = false)
    private Integer thumbs = 0;

    /**
     * 评论数
     */
    @Column(name = "comments", nullable = false)
    private Integer comments = 0;

    /**
     * 类型标签
     */
    @Column(name = "type", nullable = false)
    private String type = "";

    /**
     * 创建时间
     */
    //@JsonIgnore
    @Column(nullable = false)
    @CreatedDate
    private Timestamp gmtCreate;

    /**
     * 修改时间
     */
    @JsonIgnore
    @Column(nullable = false)
    @LastModifiedDate
    private Timestamp gmtModified;

    /**
     * 删除标志（0 逻辑删除， 1 未删除）
     */
//    @JsonIgnore
    @Column(nullable = false, length = 4)
    private Boolean isDeleted = false;
}
