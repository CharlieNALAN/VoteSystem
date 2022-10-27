package com.njupt.dto;

import com.njupt.entity.Vote;
import lombok.Data;

@Data
public class VoteDto extends Vote {
    private Integer cnt;
    private String channelName;
}
