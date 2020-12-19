package com.bais1.dao;

import com.bais1.domain.Good;

import java.util.ArrayList;
import java.util.List;

public interface GoodDao {

    /**
     * 根据cid查询总记录数
     * @param categoryId
     * @return
     */
    int getTotalCount(String goodId, String goodName, String categoryId, String supplierId);

    /**d
     * 根据cid查询一页的信息
     * @param categoryId
     * @param start
     * @param pageSize
     * @return
     */
    List<Good> getByPage(String goodId, String goodName, String categoryId, String supplierId,int start, int pageSize);

    boolean edit(String goodId,Good good);

    boolean deleteById(String goodId);

    Good getById(String goodId);

    String getMaxId();

    boolean create(Good good);

    boolean deleteByArray(ArrayList<String> goodIds);

    boolean add(String goodId,int amount);

    boolean reduce(String goodId,int amount);

    List<Good> getByArray(ArrayList<String> goodIds);

    String getNameById(String goodId);

    boolean isUsed(String goodId);
}
