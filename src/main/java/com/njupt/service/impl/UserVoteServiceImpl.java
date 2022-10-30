package com.njupt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njupt.entity.UserVote;
import com.njupt.mapper.UserVoteMapper;
import com.njupt.service.UserVoteService;
import org.springframework.stereotype.Service;

@Service
public class UserVoteServiceImpl extends ServiceImpl<UserVoteMapper, UserVote> implements UserVoteService {
}
