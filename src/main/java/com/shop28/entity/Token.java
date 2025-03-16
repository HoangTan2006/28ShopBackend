package com.shop28.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class Token {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "create_at")
    @CreationTimestamp
    private Date createAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private Date updateAt;
}
