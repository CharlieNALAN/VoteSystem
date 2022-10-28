package com.njupt.dto;


import com.njupt.entity.VoteOption;
import lombok.Data;

import java.util.List;

@Data
public class AddDto {
    private List<VoteOption> options;
    private String voteName;
    private String selectChannel;
    private String desc;
}
