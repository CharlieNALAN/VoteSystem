package com.njupt.dto;

import com.njupt.entity.VoteChannel;
import lombok.Data;

@Data
public class ChannelDto extends VoteChannel {
    int cnt; //发起投票数
}
