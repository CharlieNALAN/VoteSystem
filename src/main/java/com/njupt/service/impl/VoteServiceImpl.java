package com.njupt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njupt.entity.Vote;
import com.njupt.mapper.VoteMapper;
import com.njupt.service.VoteService;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl extends ServiceImpl<VoteMapper, Vote> implements VoteService {
}
