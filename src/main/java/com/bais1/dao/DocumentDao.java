package com.bais1.dao;

import com.bais1.domain.Document;
import com.bais1.domain.DocumentDetail;
import com.bais1.domain.DocumentType;
import com.bais1.domain.User;

import java.util.Date;
import java.util.List;

public interface DocumentDao {
    /**
     * 根据cid和gname查询总记录数
     *
     * @return
     */
    int getTotalCount(DocumentType type, String user);

    /**
     * 根据cid和gname获取一页的信息
     *
     * @param start
     * @param pageSize
     * @return
     */
    List<Document> getByPage(DocumentType type,String user, int start, int pageSize);

    /**
     * 根据did查询一个单据信息
     * @param documentId
     * @return
     */
    Document getByDid(String documentId);

    List<DocumentDetail> getDetail(String documentId);

    String create(DocumentType type, User user,float price, String note, Date time);

    boolean createDetail(String did,String good,int amount,float price);

    List<DocumentDetail> getBetweenDate(String dateStart, String dateEnd);
}
