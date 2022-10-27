package com.njupt.entity;

import lombok.Data;

@Data
public class Vote {
    private static final long serialVersionUID = 1L;
    private Integer channelId;
    private Integer id;
    private String name;
    private String createName;
    private String description;
}
