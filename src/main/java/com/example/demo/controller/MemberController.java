package com.example.demo.controller;


import com.example.demo.Member;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MemberController {


    @Autowired //注入bean 使用jdbc執行 MySQL資料庫操作
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private MemberService memberService;


    @PostMapping("/members") //設置url路徑對應到此方法上，並限制只能使用Post方法,
    public String create(@RequestBody Member member){ //使用@RequestBody取得前端requestBody資訊

        return memberService.CreatMember(member);
    }

    @GetMapping("/members")  //讀取所有會員資料
    public List<Member> read(){

        return memberService.ReadMember();
    }

    @GetMapping("members/{membersAccount}") //根據帳號做查詢
    public List<Member> read(@PathVariable String membersAccount){

        return memberService.ReadByAccount(membersAccount);
    }


    @DeleteMapping("members/{membersAccount}") //根據帳號做刪除
    public String delete(@PathVariable String membersAccount){ //@Path用來取得url路徑的值

        return memberService.DeleteByAccount(membersAccount);
    }

    @PutMapping("members/{membersAccount}") //根據帳號做修改
    public String update(@PathVariable String membersAccount,@RequestBody Member member){ //@Path用來取得url路徑的值

        return memberService.UpdateByAccount(membersAccount,member);
    }
}




    /*
    @GetMapping("/members/{memberAccount}")
    public String read(@PathVariable String memberAccount){
        return "執行read操作";
    }
    */