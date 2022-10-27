package com.njupt.entity;

import lombok.Data;

@Data
public class VoteOption {
    private static final long serialVersionUID = 1L;
    private Integer voteId;
    private Integer id;
    private String optionName;
    private Integer cnt;
    private String createName;
}
