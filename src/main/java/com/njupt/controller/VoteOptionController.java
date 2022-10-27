package com.njupt.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.njupt.common.R;
import com.njupt.entity.VoteOption;
import com.njupt.service.VoteOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/option")
public class VoteOptionController {
    @Autowired
    private VoteOptionService voteOptionService;

    @GetMapping("/{voteId}")
    public R<List<VoteOption>> options(@PathVariable Integer voteId){
        LambdaQueryWrapper<VoteOption> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VoteOption::getVoteId,voteId);
        List<VoteOption> list = voteOptionService.list(wrapper);
        return R.success(list);
    }
}
