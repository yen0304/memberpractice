package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<Member> {

    @Override
    public Member mapRow(ResultSet resultSet, int i) throws SQLException {
        //ResultSet resultSet = 從資料庫中查詢出來的數據
        //返回值= 要轉換的Java Object
            Member member=new Member();
            member.setId(resultSet.getInt("id"));
            member.setName(resultSet.getString("name"));
            member.setAccount(resultSet.getString("account"));
            member.setPassword(resultSet.getString("password"));

        return member;
    }
}
