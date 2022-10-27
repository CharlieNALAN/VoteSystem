package com.njupt.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.njupt.common.R;
import com.njupt.dto.ChannelDto;
import com.njupt.entity.User;
import com.njupt.entity.Vote;
import com.njupt.entity.VoteChannel;
import com.njupt.service.UserService;
import com.njupt.service.VoteChannelService;
import com.njupt.service.VoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/channel")
public class VoteChannelController {
    @Autowired
    private UserService userService;
    @Autowired
    private VoteChannelService voteChannelService;

    @Autowired
    private VoteService voteService;

    @GetMapping("/list")
    public R<List<ChannelDto>> list(){
        List<VoteChannel> channelList = voteChannelService.list();
//        return R.success(list);
        List<ChannelDto> list = channelList.stream().map((item)->{
            ChannelDto channelDto = new ChannelDto();
            Integer channelId = item.getId();
            LambdaQueryWrapper<Vote> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Vote::getChannelId,channelId);
            int count = voteService.count(wrapper);
            BeanUtils.copyProperties(item,channelDto);
            channelDto.setCnt(count);
            return channelDto;
        }).collect(Collectors.toList());
        return R.success(list);
    }


//    @PostMapping("/changename")
//    public R<String> changeName(@RequestBody VoteChannel voteChannel){
//        voteChannelService.updateById(voteChannel);
//        return R.success("修改成功");
//    }

//    @DeleteMapping("/{id}")
//    public R<String> delete(HttpSession session,@PathVariable Integer id){
//        String user = (String) session.getAttribute("user");
//        VoteChannel channel = voteChannelService.getById(id);
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getName,user);
//        User one = userService.getOne(wrapper);
//        if(!one.getName().equals(channel.getCreateName())){
//            return R.error("非本人创建,无法删除");
//        }
//        voteChannelService.removeById(id);
//        return R.success("删除成功");
//    }

    @GetMapping("/mine")
    public R<List<VoteChannel>> myChannel(HttpSession session){
        String username = (String) session.getAttribute("user");
        LambdaQueryWrapper<VoteChannel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VoteChannel::getCreateName,username);
        List<VoteChannel> list = voteChannelService.list(wrapper);
        return R.success(list);
    }
}
