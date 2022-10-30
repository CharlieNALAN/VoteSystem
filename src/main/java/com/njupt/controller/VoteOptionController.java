package com.njupt.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.njupt.common.R;
import com.njupt.entity.VoteOption;
import com.njupt.service.VoteOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @PostMapping("/add")
    public R<String> addOption(@RequestBody List<VoteOption> voteOptions, HttpSession session){
//        System.out.println(voteOptions);
        String user = (String) session.getAttribute("user");
        for (int i = 0; i < voteOptions.size(); i++) {
            voteOptions.get(i).setCreateName(user);
            voteOptionService.save(voteOptions.get(i));
        }
        return R.success("成功");
    }


}
