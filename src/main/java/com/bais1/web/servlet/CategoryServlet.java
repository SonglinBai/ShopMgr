package com.bais1.web.servlet;

import com.bais1.domain.Category;
import com.bais1.domain.ResultInfo;
import com.bais1.service.CategoryService;
import com.bais1.service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet{
    CategoryService service = new CategoryServiceImpl();

    public void getNameById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cidStr = request.getParameter("cid");

        writeValue(new Category(cidStr,service.getNameById(cidStr)),response);
    }

    public void getAll(HttpServletRequest request,HttpServletResponse response) throws IOException {
        List<Category> categories = service.getAll();

        writeValue(categories,response);
    }

    public void getById(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String cidStr = request.getParameter("cid");

        writeValue(service.getById(cidStr),response);
    }

    public void create(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String cidStr = request.getParameter("cid");
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        ResultInfo info = new ResultInfo();
        if(service.isIdExist(cidStr)){
            info.setFlag(false);
            info.setErrorMsg("该分类ID已经存在");
        }else {
            if(service.create(cidStr,name,description)){
                info.setFlag(true);
            }else {
                info.setFlag(false);
                info.setErrorMsg("未知错误");
            }
        }
        writeValue(info,response);

    }
    public void edit(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String ocidStr = request.getParameter("ocid");
        String cidStr = request.getParameter("cid");
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        ResultInfo info = new ResultInfo();
        if (!ocidStr.equals(cidStr)) {
            if(service.isIdExist(cidStr)) {
                info.setFlag(false);
                info.setErrorMsg("该分类ID已经存在");
            }else if(service.isUsed(ocidStr)) {
                info.setFlag(false);
                info.setErrorMsg("原分类已经被使用");
            }
        } else {
            if(service.edit(ocidStr,cidStr,name,description)){
                info.setFlag(true);
            }else {
                info.setFlag(false);
                info.setErrorMsg("未知错误");
            }
        }
        writeValue(info,response);
    }

    public void delete(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String cidStr = request.getParameter("cid");
        ResultInfo info = new ResultInfo();
        if(service.isUsed(cidStr)){
            info.setFlag(false);
            info.setErrorMsg("该分类已经被使用！");
        }else {
            if(service.delete(cidStr)){
                info.setFlag(true);
            }else {
                info.setFlag(false);
                info.setErrorMsg("未知错误");
            }
        }
        writeValue(info,response);
    }
}
