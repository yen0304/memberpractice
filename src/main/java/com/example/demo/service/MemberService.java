package com.example.demo.service;


import com.example.demo.Member;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface MemberService {

    String CreatMember(Member member);
    List<Member> ReadMember();
    List<Member> ReadByAccount(String membersAccount);
    String DeleteByAccount(String membersAccount);
    String UpdateByAccount(String membersAccount,Member member); //@Path用來取得url路徑的值

    }
