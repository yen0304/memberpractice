package com.example.demo.service;


import com.example.demo.Member;
import com.example.demo.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component
public class MemberServiceImpl implements MemberService{


    @Autowired//使用InterFace 發揮spring Ioc特性
    private MemberDao memberDao;

    @Override
    public String CreatMember(Member member) {
        return memberDao.CreatMember(member);
    }

    @Override
    public List<Member> ReadMember() {
        return memberDao.ReadMember();
    }

    @Override
    public List<Member> ReadByAccount(String membersAccount) {
        return memberDao.ReadByAccount(membersAccount);
    }

    @Override
    public String DeleteByAccount(String membersAccount) {
        return memberDao.DeleteByAccount(membersAccount);
    }

    @Override
    public String UpdateByAccount(String membersAccount,Member member) {
        return memberDao.UpdateByAccount(membersAccount,member);
    }
}
