package com.njupt.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.njupt.common.R;
import com.njupt.dto.AddDto;
import com.njupt.dto.VoteDto;
import com.njupt.entity.Vote;
import com.njupt.entity.VoteChannel;
import com.njupt.entity.VoteOption;
import com.njupt.service.VoteChannelService;
import com.njupt.service.VoteOptionService;
import com.njupt.service.VoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @Autowired
    private VoteOptionService voteOptionService;

    @Autowired
    private VoteChannelService voteChannelService;

    @GetMapping("/list/{channelId}")
    public R<List<VoteDto>> list(@PathVariable Integer channelId){
        LambdaQueryWrapper<Vote> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Vote::getChannelId,channelId);
        List<Vote> list = voteService.list(wrapper1);
        List<VoteDto> voteDtos = list.stream().map((item) -> {
            VoteDto voteDto = new VoteDto();
            int count = 0;
            LambdaQueryWrapper<VoteOption> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(VoteOption::getVoteId, item.getId());
            List<VoteOption> options = voteOptionService.list(wrapper);
            for (VoteOption option : options) {
                count += option.getCnt();
            }
            BeanUtils.copyProperties(item, voteDto);
            voteDto.setCnt(count);
            return voteDto;
        }).collect(Collectors.toList());
        return R.success(voteDtos);
    }

    @GetMapping("/{id}")
    public R<Vote> detail(@PathVariable Integer id){
//        LambdaQueryWrapper<Vote> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Vote::getId,id);
        Vote vote = voteService.getById(id);
        if(vote == null){
            return R.error("未找到");
        }
        return R.success(vote);
    }

    @GetMapping("/mine")
    public R<List<VoteDto>> myChannel(HttpSession session){
        String username = (String) session.getAttribute("user");
        LambdaQueryWrapper<Vote> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Vote::getCreateName,username);
        List<Vote> list = voteService.list(wrapper);
        List<VoteDto> voteDtos = list.stream().map((item) -> {
            VoteDto voteDto = new VoteDto();
            VoteChannel channel = voteChannelService.getById(item.getChannelId());
            int count = 0;
            LambdaQueryWrapper<VoteOption> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(VoteOption::getVoteId, item.getId());
            List<VoteOption> options = voteOptionService.list(wrapper1);
            for (VoteOption option : options) {
                count += option.getCnt();
            }
            BeanUtils.copyProperties(item, voteDto);
            voteDto.setChannelName(channel.getName());
            voteDto.setCnt(count);
            return voteDto;
        }).collect(Collectors.toList());
        return R.success(voteDtos);
    }

    @PostMapping("/add")
    public R<String> add(@RequestBody VoteDto voteDto,HttpSession session){
//        System.out.println(voteDto);
//        return null;
        String user = (String) session.getAttribute("user");
        Vote vote = new Vote();
        vote.setCreateName(user);
        LambdaQueryWrapper<VoteChannel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VoteChannel::getName,voteDto.getChannelName());
        VoteChannel channel = voteChannelService.getOne(wrapper);
        vote.setChannelId(channel.getId());
        vote.setName(voteDto.getName());
        vote.setDescription(voteDto.getDescription());
//        Random random = new Random();
//        int id = Math.abs(random.nextInt());
//        LambdaQueryWrapper<Vote> wrapper1 = new LambdaQueryWrapper<>();
//        wrapper1.eq(Vote::getId,id);
//        int count = voteService.count(wrapper1);
//        while(count!=0){
//            id = Math.abs(random.nextInt());
//            count = voteService.count(wrapper1);
//        }
//        vote.setId(id);
        voteService.save(vote);

//        LambdaQueryWrapper<Vote> wrapper1 = new LambdaQueryWrapper<>();
//        wrapper1.eq(Vote::getChannelId,channel.getId());
//        wrapper1.eq(Vote::getCreateName,user);
//        wrapper1.eq(Vote::getName,voteDto.getName());
//        vote = voteService.getOne(wrapper1);
        return R.success(vote.getId().toString());
    }
}
