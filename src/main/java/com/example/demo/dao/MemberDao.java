package com.example.demo.dao;


import com.example.demo.Member;
import org.springframework.ui.Model;

import java.util.List;

public interface MemberDao {

    //返回值 依據名稱對應資料庫動作（參數類型 參數名稱）
    String CreatMember(Member member);

    List<Member> ReadMember();
    List<Member> ReadByAccount(String membersAccount);
    String DeleteByAccount(String membersAccount);
    String UpdateByAccount(String membersAccount,Member member);
}
