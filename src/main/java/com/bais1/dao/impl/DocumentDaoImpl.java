package com.bais1.dao.impl;

import com.bais1.dao.DocumentDao;
import com.bais1.domain.*;
import com.bais1.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DocumentDaoImpl implements DocumentDao {
    //获取jdbctemplate
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int getTotalCount(DocumentType type, String user) {
        String sql = "select count(*) from tb_document where 1=1";
        //用于拼接查询语句
        StringBuilder stringBuilder = new StringBuilder(sql);
        //用于存储参数信息
        ArrayList params = new ArrayList<>();
        String typeStr = null;
        if(type!=null)
            typeStr = type.equals(DocumentType.SALE)?"SALE":"PURCHASE";

        if (typeStr!=null) {
            stringBuilder.append(" and type = ? ");
            params.add(typeStr);
        }
        if (user!=null&&user.length()>0) {
            stringBuilder.append(" and userAccount = ?");
            params.add(user);
        }
        sql = stringBuilder.toString();
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Document> getByPage(DocumentType type, String user,int start, int pageSize) {
        String sql = "select * from tb_document where 1=1";
        //用于拼接查询语句
        StringBuilder stringBuilder = new StringBuilder(sql);
        //用于存储参数信息
        ArrayList params = new ArrayList<>();
        String typeStr = null;
        if(type!=null) {
            typeStr = type.equals(DocumentType.SALE) ? "SALE" : "PURCHASE";
        }

        if (typeStr!=null) {
            stringBuilder.append(" and type = ?");
            params.add(typeStr);
        }
        if (user!=null&&user.length()>0) {
            stringBuilder.append(" and userAccount = ?");
            params.add(user);
        }
        stringBuilder.append(" limit ?,?");
        params.add(start);
        params.add(pageSize);

        sql = stringBuilder.toString();
        return template.query(sql, new BeanPropertyRowMapper<Document>(Document.class), params.toArray());
    }


    @Override
    public Document getByDid(String documentId) {
        String sql = "select * from tb_document where documentId = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Document>(Document.class),documentId);
    }

    @Override
    public List<DocumentDetail> getDetail(String documentId) {
        String sql = "select * from tb_document_detail where documentId = ?";

        return template.query(sql,new BeanPropertyRowMapper<DocumentDetail>(DocumentDetail.class),documentId);
    }

    @Override
    public String create(final DocumentType type, User user,float price, String note, Date time) {
        String sql = "insert into tb_document (documentId, type, userAccount,price, note, orderTime) VALUES (?,?,?,?,?,?)";
        String t = type.equals(DocumentType.SALE)?"S":"P";
        String did = t+ String.valueOf(time.getTime());
        String typeStr;
        try {
            typeStr=type.equals(DocumentType.SALE)?"SALE":"PURCHASE";
            template.update(sql,did,typeStr,user.getUserAccount(),price,note, new Timestamp(time.getTime()));
        } catch (DataAccessException e) {
            return null;
        };
        return did;
    }

    @Override
    public boolean createDetail(String documentId, String good, int amount,float price) {
        String sql = "insert into tb_document_detail (documentId, goodId, amount,price) values (?,?,?,?)";

        try {
            template.update(sql,documentId,good,amount,price);
        } catch (DataAccessException e) {
            return false;
        }return true;
    }

    @Override
    public List<DocumentDetail> getBetweenDate(String dateStart, String dateEnd) {
        String sql = "select * from tb_document_detail where documentId in (select documentId from tb_document";
        StringBuilder stringBuilder = new StringBuilder(sql);
        ArrayList params = new ArrayList<>();
        if(dateStart!=null&&dateEnd!=null){
            stringBuilder.append(" where type='SALE' and orderTime between ? and ?");
            params.add(dateStart);
            params.add(dateEnd);
        }
        stringBuilder.append(")");
        sql = stringBuilder.toString();
        return template.query(sql,new BeanPropertyRowMapper<DocumentDetail>(DocumentDetail.class),params.toArray());
    }
}
