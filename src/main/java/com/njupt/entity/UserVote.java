package com.njupt.entity;

import lombok.Data;

@Data
public class UserVote {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String userName;
    private Integer voteId;
    private Integer voteOptionId;
}
