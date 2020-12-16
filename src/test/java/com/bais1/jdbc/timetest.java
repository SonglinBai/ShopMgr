package com.bais1.jdbc;

import com.bais1.domain.Document;
import com.bais1.util.JDBCUtils;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.util.List;


public class timetest {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

/*
    @Test
    public void timeTest() {

        String sql = "insert into tab_document (did, good, amount, ex_im, user, time) VALUES (null,1,10,0,1,?)";

        try {
            template.update(sql,new Date());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        String sql = "select * from tab_document,tab_good where good=gid ";

        StringBuilder stringBuilder = new StringBuilder(sql);

        stringBuilder.append(" and category = ? ");
        stringBuilder.append(" and name like ?");

        sql =  stringBuilder.toString();


        List<Document> list = template.query(sql, new BeanPropertyRowMapper<Document>(Document.class),1,"%test%");
        System.out.println(list);
        System.out.println(list.get(0).getTime().getClass().getName());
        System.out.println(list.get(0).getTime());
    }

*/
    @Test
    public void test1() {
        System.out.println(System.currentTimeMillis());

        System.out.println(new Date(System.currentTimeMillis()));
    }
}
