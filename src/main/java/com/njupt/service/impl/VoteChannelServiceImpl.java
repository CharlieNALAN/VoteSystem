package com.njupt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njupt.entity.VoteChannel;
import com.njupt.mapper.VoteChannelMapper;
import com.njupt.service.VoteChannelService;
import org.springframework.stereotype.Service;

@Service
public class VoteChannelServiceImpl extends ServiceImpl<VoteChannelMapper, VoteChannel> implements VoteChannelService {
}
