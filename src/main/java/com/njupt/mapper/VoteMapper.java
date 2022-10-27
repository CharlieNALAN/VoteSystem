package com.njupt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njupt.entity.Vote;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VoteMapper extends BaseMapper<Vote> {
}
