package com.njupt.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.njupt.common.R;
import com.njupt.dto.UserDto;
import com.njupt.entity.User;
import com.njupt.entity.Vote;
import com.njupt.entity.VoteOption;
import com.njupt.service.UserService;
import com.njupt.service.VoteOptionService;
import com.njupt.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private VoteOptionService voteOptionService;

    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody User user){
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName,user.getName());
        User u = userService.getOne(queryWrapper);
        if(u == null){
            return R.error("登陆失败");
        }
        if(!u.getPassword().equals(password)){
            return R.error("登陆失败");
        }
        HttpSession session = request.getSession();
        session.setAttribute("user",user.getName());
        return R.success(user);
    }

    @PostMapping("/register")
    public R<String> register(@RequestBody User user){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName,user.getName());
        User one = userService.getOne(queryWrapper);
        if(one != null){
            return R.error("用户已存在，无法注册");
        }
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        User user1 = new User();
        user1.setName(user.getName());
        user1.setPassword(password);
        userService.save(user1);
        return R.success("注册成功");
    }

//    @GetMapping("/detail")
//    public R<User> detail(HttpSession session){
//        String user = (String) session.getAttribute("user");
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getName,user);
//        User one = userService.getOne(wrapper);
//        return R.success(one);
//    }

    @GetMapping("/count")
    public R<UserDto> count(HttpSession session){
        UserDto userDto = new UserDto();
        int count = userService.count();
        userDto.setCnt(count);

        String name = (String) session.getAttribute("user");
        LambdaQueryWrapper<Vote> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Vote::getCreateName,name);
        int numOfVote = voteService.count(wrapper);
        userDto.setNumOfVote(numOfVote);

        LambdaQueryWrapper<VoteOption> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(VoteOption::getCreateName,name);
        List<VoteOption> list = voteOptionService.list(wrapper1);
        int sum = 0;
        for(int i=0;i<list.size();i++){
            sum+=list.get(i).getCnt();
        }
        userDto.setNumOfPeople(sum);
        return R.success(userDto);
    }

    @GetMapping("/logout")
    public R<String> logOut(HttpSession session){
        session.removeAttribute("user");
        return R.success("success");
    }

}
