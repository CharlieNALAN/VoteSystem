package com.njupt.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.njupt.common.R;
import com.njupt.entity.UserVote;
import com.njupt.entity.Vote;
import com.njupt.entity.VoteOption;
import com.njupt.service.UserVoteService;
import com.njupt.service.VoteOptionService;
import com.njupt.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usvt")
public class UserVoteCntroller {
    @Autowired
    private UserVoteService userVoteService;

    @Autowired
    private VoteOptionService voteOptionService;

    @Autowired
    private VoteService voteService;

    @GetMapping("/check/{id}")
    public R<String> check(@PathVariable Integer id, HttpSession session){
        String user = (String) session.getAttribute("user");
        LambdaQueryWrapper<UserVote> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserVote::getUserName,user);
        wrapper.eq(UserVote::getVoteId,id);
        int count = userVoteService.count(wrapper);
        if(count!=0){
            return R.error("已投票");
        }else{
            return R.success("未投票");
        }
    }

    @GetMapping("/participate")
    public R<List<Vote>> participate(HttpSession session){
        String user = (String) session.getAttribute("user");
        LambdaQueryWrapper<UserVote> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserVote::getUserName,user);
        List<UserVote> userVotes = userVoteService.list(wrapper);
//        ArrayList<Vote> votes = new ArrayList<>();
        List<Vote> votes = userVotes.stream().map((item)->{
            Integer voteId = item.getVoteId();
            return voteService.getById(voteId);
        }).collect(Collectors.toList());
        return R.success(votes);
    }

    @PostMapping
    public R<String> voteFor(@RequestBody VoteOption voteOption,HttpSession session){
        String user = (String) session.getAttribute("user");
//        VoteOption option = new VoteOption();
        voteOption = voteOptionService.getById(voteOption.getId());
        voteOption.setCnt(voteOption.getCnt()+1);
        voteOptionService.updateById(voteOption);
        int voteId = voteOption.getVoteId();
        UserVote userVote = new UserVote();
        userVote.setUserName(user);
        userVote.setVoteOptionId(voteOption.getId());
        userVote.setVoteId(voteOption.getVoteId());
        userVoteService.save(userVote);
        return R.success("投票完成");
    }
}
