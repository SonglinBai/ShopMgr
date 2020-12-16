package com.bais1.service;

import com.bais1.domain.*;

import java.util.List;

public interface DocumentService {
    PageBean<Document> pageQuery(DocumentType type, String user, int currentPage, int pageSize);

    List<DocumentDetail> getDetail(String documentId);

}
