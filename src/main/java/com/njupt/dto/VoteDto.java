package com.njupt.dto;

import com.njupt.entity.Vote;
import lombok.Data;

@Data
public class VoteDto extends Vote {
    private Integer cnt;
    private String channelName;


    @Override
    public String toString() {
        return "VoteDto{" +
                "cnt=" + cnt +
                ", channelName='" + channelName + '\'' +
                "} " + super.toString();
    }
}
