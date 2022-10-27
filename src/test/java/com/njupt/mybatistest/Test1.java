package com.njupt.mybatistest;

import com.njupt.entity.VoteChannel;
import com.njupt.service.VoteChannelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Test1 {

    @Autowired
    VoteChannelService voteChannelService;

    @Test
    public void test1(){
        List<VoteChannel> list = voteChannelService.list();

        System.out.println(list.get(0).getName());

    }
}
