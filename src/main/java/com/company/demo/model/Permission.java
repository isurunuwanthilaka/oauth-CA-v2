package com.company.demo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "auth_permission")
@Data
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String permissionName;
}

