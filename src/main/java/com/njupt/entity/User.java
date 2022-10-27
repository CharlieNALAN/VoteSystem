package com.njupt.entity;

import lombok.Data;

@Data
public class User {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String password;
}
