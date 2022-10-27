package com.njupt.dto;

import com.njupt.entity.User;
import lombok.Data;

@Data
public class UserDto extends User {
    private Integer cnt;
    private Integer numOfVote;
    private Integer numOfPeople;
}
