package com.bais1.service;

import com.bais1.domain.Good;
import com.bais1.domain.PageBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface GoodService {
    //获取单页对象
    PageBean<Good> pageQuery(String goodId, String goodName, String categoryId, String supplierId, int currentPage,int pageSize);
    //编辑
    boolean edit(String oldGoodId,Good good);
    //删除
    boolean deleteById(String goodId);

    Good getById(String goodId);

    String getNextId();

    boolean create(Good good);

    boolean deleteByArray(ArrayList<String> goodIds);

    boolean purchaseGood(Map<String,Integer> goods,String user);

    boolean saleGood(Map<String,Integer> goods,String user);

    List<Good> getByArray(ArrayList<String> goodIds);

    boolean isIdExist(Good good);

    boolean isUsed(String gid);
}
