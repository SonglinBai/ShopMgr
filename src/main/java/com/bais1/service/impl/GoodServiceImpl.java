package com.bais1.service.impl;

import com.bais1.dao.CategoryDao;
import com.bais1.dao.DocumentDao;
import com.bais1.dao.GoodDao;
import com.bais1.dao.SupplierDao;
import com.bais1.dao.impl.CategoryDaoImpl;
import com.bais1.dao.impl.DocumentDaoImpl;
import com.bais1.dao.impl.GoodDaoImpl;
import com.bais1.dao.impl.SupplierDaoImpl;
import com.bais1.domain.Category;
import com.bais1.domain.DocumentType;
import com.bais1.domain.Good;
import com.bais1.domain.PageBean;
import com.bais1.service.GoodService;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoodServiceImpl implements GoodService {
    GoodDao goodDao = new GoodDaoImpl();
    CategoryDao categoryDao = new CategoryDaoImpl();
    DocumentDao documentDao = new DocumentDaoImpl();
    SupplierDao supplierDao = new SupplierDaoImpl();

    @Override
    public PageBean<Good> pageQuery(String goodId, String goodName, String categoryId, String supplierId, int currentPage, int pageSize) {
        PageBean<Good> pb = new PageBean<Good>();

        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);

        int totalCount = goodDao.getTotalCount(goodId, goodName, categoryId, supplierId);
        pb.setTotalCount(totalCount);

        int start = (currentPage - 1) * pageSize;

        List<Good> list = goodDao.getByPage(goodId, goodName, categoryId, supplierId, start, pageSize);
        for (Good good : list) {
            good.setCategory(categoryDao.getById(good.getCategoryId()));
            good.setSupplier(supplierDao.getById(good.getSupplierId()));
        }
        pb.setList(list);

        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public boolean edit(String oldGoodId, Good good) {

        return goodDao.edit(oldGoodId, good);
    }

    @Override
    public boolean deleteById(String goodId) {
        return goodDao.deleteById(goodId);
    }

    @Override
    public Good getById(String goodId) {
        return goodDao.getById(goodId);
    }

    @Override
    public String getNextId() {
        return goodDao.getMaxId() + 1;
    }

    @Override
    public boolean create(Good good) {
        return goodDao.create(good);
    }

    @Override
    public boolean deleteByArray(ArrayList<String> goodIds) {
        return goodDao.deleteByArray(goodIds);
    }

    @Override
    public boolean purchaseGood(Map<String, Integer> goods, String user) {
        boolean flag = true;
        String documentId = documentDao.create(DocumentType.PURCHASE, user, new Date(System.currentTimeMillis()));
        if (documentId == null) flag = false;
        for (Map.Entry<String, Integer> good : goods.entrySet()) {
            if (!documentDao.createDetail(documentId, good.getKey(), good.getValue()) || !goodDao.add(good.getKey(), good.getValue()))
                flag = false;
        }
        return flag;
    }

    @Override
    public boolean saleGood(Map<String, Integer> goods, String user) {
        boolean flag = true;
        String documentId = documentDao.create(DocumentType.SALE, user, new Date(System.currentTimeMillis()));
        if (documentId == null) flag = false;
        for (Map.Entry<String, Integer> good : goods.entrySet()) {
            if (!documentDao.createDetail(documentId, good.getKey(), good.getValue()) || !goodDao.reduce(good.getKey(), good.getValue()))
                flag = false;
        }
        return flag;
    }

    @Override
    public List<Good> getByArray(ArrayList<String> goodIds) {
        List<Good> goods = goodDao.getByArray(goodIds);
        for (Good good : goods) {
            good.setCategory(new Category(good.getCategoryId(), categoryDao.getNameById(good.getCategoryId())));
        }
        return goods;
    }

    @Override
    public boolean isIdExist(Good good) {
        Good g = goodDao.getById(good.getGoodId());

        if (g != null) return true;
        else return false;
    }

    @Override
    public boolean isUsed(String goodId) {
        return goodDao.isUsed(goodId);
    }
}
