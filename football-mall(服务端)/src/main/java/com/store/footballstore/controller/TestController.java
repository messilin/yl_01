package com.store.footballstore.controller;


import com.store.footballstore.entity.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hhh")
public class TestController {
/*
* 测试
* */
    @GetMapping("/test")
    public R test(){
        return R.ok("成功");
    }
}
