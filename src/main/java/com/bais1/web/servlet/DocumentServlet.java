package com.bais1.web.servlet;


import com.bais1.domain.Document;
import com.bais1.domain.DocumentDetail;
import com.bais1.domain.DocumentType;
import com.bais1.domain.PageBean;
import com.bais1.service.DocumentService;
import com.bais1.service.impl.DocumentServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/document/*")
public class DocumentServlet extends BaseServlet{
    private DocumentService documentService = new DocumentServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //1.接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String ex_imStr = request.getParameter("ex_im");
        String userStr = request.getParameter("user");


        //2.处理参数
        int currentPage = 0;//当前页码，如果不传递，则默认为第一页
        if(currentPageStr != null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }

        int pageSize = 0;//每页显示条数，如果不传递，默认每页显示5条记录
        if(pageSizeStr != null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;
        }

        DocumentType ex_im;
        if(ex_imStr != null && ex_imStr.length() > 0){
            ex_im = ex_imStr.equals("SALE")?DocumentType.SALE:DocumentType.PURCHASE;
        }else{
            ex_im = null;
        }

        //3. 调用service查询PageBean对象
        PageBean<Document> pb = documentService.pageQuery(ex_im,userStr,currentPage, pageSize);

        //4. 将pageBean对象序列化为json，返回
        writeValue(pb,response);
    }

    public void getDetail(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String didStr = request.getParameter("did");

        List<DocumentDetail> details = documentService.getDetail(didStr);

        writeValue(details,response);
    }


}
