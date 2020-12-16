package com.bais1.dao.impl;

import com.bais1.dao.DocumentDao;
import com.bais1.domain.Document;
import com.bais1.domain.DocumentDetail;
import com.bais1.domain.DocumentType;
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

        if (type!=null) {
            stringBuilder.append(" and type = ? ");
            params.add(type);
        }
        if (!user.equals("0")) {
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

        if (type!=null) {
            stringBuilder.append(" and ex_im = ?");
            params.add(type);
        }
        if (!user.equals("0")) {
            stringBuilder.append(" and user = ?");
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
/*

获取自增长id
public Emp create(final Emp emp){
		final String sql = "insert into Emp (age,name)values(?,?)";
		KeyHolder holder = new GeneratedKeyHolder();
		jdbc.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, emp.getAge());
				ps.setString(2, emp.getName());
				return ps;
			}
		},holder);

		int newEmpId= holder.getKey().intValue();
		emp.setId(newEmpId);
		return emp;
	}
 */


    //TODO: auto generate documentId
    @Override
    public String create(final DocumentType type, final String user, final Date time) {
        final String sql = "insert into tb_document (type, userAccount, orderTime) VALUES (?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        String did;

        try {
            template.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1,type.name());
                    ps.setString(2,user);
                    ps.setTimestamp(3,new Timestamp(time.getTime()));
                    return ps;
                }
            },holder);
            did = holder.getKey().toString();
        } catch (DataAccessException e) {
            return null;
        };
        return did;
    }

    @Override
    public boolean createDetail(String documentId, String good, int amount) {
        String sql = "insert into tb_document_detail (documentId, goodId, amount) values (?,?,?)";

        try {
            template.update(sql,documentId,good,amount);
        } catch (DataAccessException e) {
            return false;
        }return true;
    }
}
