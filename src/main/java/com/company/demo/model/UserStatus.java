package com.company.demo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "auth_user_status")
@Data
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private UserStatusEnum statusName;
}

