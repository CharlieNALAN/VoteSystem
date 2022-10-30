package com.njupt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njupt.entity.UserVote;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface UserVoteMapper extends BaseMapper<UserVote> {
}
