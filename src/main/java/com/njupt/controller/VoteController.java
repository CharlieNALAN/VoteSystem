package com.njupt.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.njupt.common.R;
import com.njupt.dto.VoteDto;
import com.njupt.entity.Vote;
import com.njupt.entity.VoteChannel;
import com.njupt.entity.VoteOption;
import com.njupt.service.VoteChannelService;
import com.njupt.service.VoteOptionService;
import com.njupt.service.VoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
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

    @GetMapping("/list")
    public R<List<VoteDto>> list(){
        List<Vote> list = voteService.list();
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
            BeanUtils.copyProperties(item, voteDto);
            voteDto.setChannelName(channel.getName());
            return voteDto;
        }).collect(Collectors.toList());
        return R.success(voteDtos);
    }
}
