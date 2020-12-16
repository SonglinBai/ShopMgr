package com.bais1.service.impl;

import com.bais1.dao.DocumentDao;
import com.bais1.dao.GoodDao;
import com.bais1.dao.UserDao;
import com.bais1.dao.impl.DocumentDaoImpl;
import com.bais1.dao.impl.GoodDaoImpl;
import com.bais1.dao.impl.UserDaoImpl;
import com.bais1.domain.*;
import com.bais1.service.DocumentService;

import java.util.List;

public class DocumentServiceImpl implements DocumentService {
    DocumentDao documentDao = new DocumentDaoImpl();
    UserDao userDao = new UserDaoImpl();
    GoodDao goodDao = new GoodDaoImpl();
    @Override
    public PageBean<Document> pageQuery(DocumentType type, String user, int currentPage, int pageSize) {
        PageBean<Document> pb = new PageBean<Document>();

        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);

        int totalCount = documentDao.getTotalCount(type,user);
        pb.setTotalCount(totalCount);

        int start = (currentPage - 1) * pageSize;

        List<Document> list = documentDao.getByPage(type,user, start, pageSize);
        for (Document document : list) {
            document.setUser(new User(document.getUserAccount(),userDao.getNameByAccount(document.getUserAccount())));
        }
        pb.setList(list);

        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public List<DocumentDetail> getDetail(String documentId) {
        List<DocumentDetail> details = documentDao.getDetail(documentId);
        for (DocumentDetail detail : details) {
            detail.setGood(new Good(detail.getGoodId(),goodDao.getNameById(detail.getGoodId())));
        }
        return details;
    }

}
