package com.njupt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njupt.entity.VoteOption;
import com.njupt.mapper.VoteOptionMapper;
import com.njupt.service.VoteChannelService;
import com.njupt.service.VoteOptionService;
import org.springframework.stereotype.Service;

@Service
public class VoteOptionServiceImpl extends ServiceImpl<VoteOptionMapper, VoteOption> implements VoteOptionService {
}
