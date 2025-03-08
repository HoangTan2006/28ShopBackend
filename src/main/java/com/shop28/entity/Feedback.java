package com.shop28.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feedbacks")
public class Feedback extends AbstractEntity<Integer> {
    @JoinColumn(name = "order_detail_id")
    @ManyToOne
    private OrderDetail orderDetail;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "star")
    private Integer star;

    @Column(name = "comment")
    private String note;
}
