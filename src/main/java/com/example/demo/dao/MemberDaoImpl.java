package com.example.demo.dao;


import com.example.demo.Member;
import com.example.demo.MemberRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component //將class交給Bean
public class MemberDaoImpl implements MemberDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String CreatMember(Member member) {
        String sql ="INSERT INTO member(name,account,password) VALUE(:memberName,:memberAccount,:memberPassword)";// 在SQL前面加上：表示變數
        Map<String, Object> map =new HashMap<>();

        map.put("memberId",member.getId());     //put(SQL變數,值）
        map.put("memberName",member.getName());
        map.put("memberAccount",member.getAccount());
        map.put("memberPassword",member.getPassword());

        namedParameterJdbcTemplate.update(sql,map);
        return "註冊成功"; //return放將來要使用的html
    }

    @Override
    //重要！當方法返回類型為自定義class,spring boot再返回時會自動轉換為json格式
    public List<Member> ReadMember() {
        //query(放要執行sql的語法,放spl語法裡面變數的值,將資料查詢出來的數據轉換成java object)
        //SELECT id, name...這邊是想要查詢的數據，不要用SELECT * FROM 查詢所有數據，很浪費流量
        String sql="SELECT id, name ,password ,account FROM member";

        Map<String,Object>map= new HashMap<>();

        List<Member> list =namedParameterJdbcTemplate.query(sql,map,new MemberRowMapper());

        return list;
    }

    @Override
    public List<Member> ReadByAccount(String membersAccount) {
        String sql="SELECT id, name ,password ,account FROM member WHERE account=:memberAccount";

        Map<String,Object>map= new HashMap<>();
        map.put("memberAccount",membersAccount);

        List<Member> list =namedParameterJdbcTemplate.query(sql,map,new MemberRowMapper());

        return list;
    }

    @Override
    public String UpdateByAccount(String membersAccount,Member member) {
        String sql ="UPDATE member SET name=:memberName,password=:memberPassword WHERE account=:memberAccount";

        Map<String, Object> map =new HashMap<>();
        //put(SQL變數,值）
        map.put("memberName",member.getName());
        map.put("memberAccount",membersAccount);
        map.put("memberPassword",member.getPassword());
        namedParameterJdbcTemplate.update(sql,map);



        return "修改成功";
    }

    @Override
    public String DeleteByAccount(String membersAccount) {

        String sql ="DELETE FROM member WHERE account = :membersAccount";
        Map<String,Object>map= new HashMap<>();
        map.put("membersAccount",membersAccount);
        namedParameterJdbcTemplate.update(sql,map);

        return "刪除成功";
    }


}
