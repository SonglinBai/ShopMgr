package com.bais1.dao.impl;

import com.bais1.dao.GoodDao;
import com.bais1.domain.Good;
import com.bais1.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class GoodDaoImpl implements GoodDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int getTotalCount(String categoryId, String goodName) {
        String sql = "select count(*) from tb_good where 1=1 ";
        //用于拼接查询语句
        StringBuilder stringBuilder = new StringBuilder(sql);
        //用于存储参数信息
        ArrayList params = new ArrayList<>();

        if (!categoryId.equals("0")) {
            stringBuilder.append(" and category = ? ");
            params.add(categoryId);
        }
        if (goodName != null && goodName.length() > 0) {
            stringBuilder.append(" and name like ?");
            params.add("%" + goodName + "%");
        }
        sql = stringBuilder.toString();
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Good> getByPage(String categoryId, String goodName, int start, int pageSize) {
        String sql = "select * from tb_good where 1=1 ";
        //用于拼接查询语句
        StringBuilder stringBuilder = new StringBuilder(sql);
        //用于存储参数信息
        ArrayList params = new ArrayList<>();

        if (!categoryId.equals("0")) {
            stringBuilder.append(" and category = ? ");
            params.add(categoryId);
        }
        if (goodName != null && goodName.length() > 0) {
            stringBuilder.append(" and name like ?");
            params.add("%" + goodName + "%");
        }
        stringBuilder.append(" limit ?,?");
        params.add(start);
        params.add(pageSize);

        sql = stringBuilder.toString();
        return template.query(sql, new BeanPropertyRowMapper<Good>(Good.class), params.toArray());
    }

    @Override
    public boolean edit(String goodId, Good good) {
        String sql = "update tb_good set goodId=?,goodName=?,categoryId=?,description=? where goodId=?";

        try {
            template.update(sql, good.getGoodId(), good.getGoodName(), good.getCategory(), good.getDescription(), goodId);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteById(String goodId) {
        String sql = "delete from tb_good where goodId=?";

        try {
            template.update(sql, goodId);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public Good getById(String goodId) {
        String sql = "select * from tb_good where goodId=?";
        Good good = null;
        try {
            good = template.queryForObject(sql, new BeanPropertyRowMapper<Good>(Good.class), goodId);
        } catch (DataAccessException e) {
            System.out.println("didn't get good by id" + goodId);
        }
        return good;
    }

    @Override
    public String getMaxId() {
        String sql = "select MAX(goodId) from tb_good";
        String maxId = "0";
        try {
            maxId = template.queryForObject(sql, String.class);
        } catch (DataAccessException e) {
            System.out.println("can't get maxId");
        }
        return maxId;
    }

    @Override
    public boolean create(Good good) {
        String sql = "insert into tb_good (goodId, goodName, categoryId, inventory, description) values (?,?,?,?,?)";
        try {
            template.update(sql, good.getGoodId(), good.getGoodName(), good.getCategory(), good.getInventory(), good.getDescription());
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByArray(ArrayList<String> gids) {
        String sql = "delete from tb_good where 1!=1";

        StringBuilder stringBuilder = new StringBuilder(sql);
        ArrayList params = new ArrayList<>();
        for (String gid : gids) {
            stringBuilder.append(" or gid=?");
            params.add(gid);
        }
        sql = stringBuilder.toString();
        try {
            template.update(sql, params.toArray());
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean add(String goodId, int amount) {
        String sql = "update tb_good set inventory=inventory+? where goodId=?";

        try {
            template.update(sql, amount, goodId);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean reduce(String goodId, int amount) {
        String sql = "update tb_good set inventory=inventory-? where goodId=?";

        try {
            template.update(sql, amount, goodId);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Good> getByArray(ArrayList<String> gids) {
        String sql = "select * from tb_good where 1!=1";

        StringBuilder stringBuilder = new StringBuilder(sql);
        ArrayList params = new ArrayList<>();
        for (String gid : gids) {
            stringBuilder.append(" or gid=?");
            params.add(gid);
        }
        sql = stringBuilder.toString();
        return template.query(sql, new BeanPropertyRowMapper<Good>(Good.class), params.toArray());
    }

    @Override
    public String getNameById(String goodId) {
        String sql = "select goodName from tb_good where goodId=?";

        return template.queryForObject(sql, String.class, goodId);
    }

    @Override
    public boolean isUsed(String gid) {
        String sql = "select count(documentId) from tb_document_detail where goodId=?";

        Integer count = template.queryForObject(sql, Integer.class, gid);

        if (count==0) return false;
        else return true;
    }

}
