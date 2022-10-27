package com.njupt;

import com.njupt.entity.VoteChannel;
import com.njupt.service.VoteChannelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class VoteSystemApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    VoteChannelService voteChannelService;

    @Test
    public void test1(){
        List<VoteChannel> list = voteChannelService.list();

        System.out.println(list.get(0).getName());

    }

    @Test
    public void test2(){
        VoteChannel voteChannel = new VoteChannel();
        voteChannel.setName("test2");
        voteChannelService.save(voteChannel);
    }
}
