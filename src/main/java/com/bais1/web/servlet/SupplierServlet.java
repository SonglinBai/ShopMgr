package com.bais1.web.servlet;


import com.bais1.domain.ResultInfo;
import com.bais1.domain.Supplier;
import com.bais1.service.SupplierService;
import com.bais1.service.impl.SupplierServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/supplier/*")
public class SupplierServlet extends BaseServlet{
    SupplierService service = new SupplierServiceImpl();

    public void getNameById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String supplierId = request.getParameter("supplierId");

        writeValue(new Supplier(supplierId, service.getNameById(supplierId)), response);
    }

    public void getAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Supplier> suppliers = service.getAll();
        writeValue(suppliers, response);
    }

    public void getById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String supplierId = request.getParameter("supplierId");

        writeValue(service.getById(supplierId), response);
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String supplierId = request.getParameter("supplierId");
        String name = request.getParameter("supplierName");
        String addr = request.getParameter("address");
        String phone = request.getParameter("phone");

        ResultInfo info = new ResultInfo();
        if(service.isIdExist(supplierId)) {
            info.setFlag(false);
            info.setErrorMsg("改供应商ID已经被使用，请更换一个");
        }else {
            if(service.create(supplierId, name, addr, phone)) {
                info.setFlag(true);
            }else {
                info.setFlag(false);
                info.setErrorMsg("未知错误");
            }
        }
        writeValue(info,response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String oldSupplierId = request.getParameter("oldSupplierId");
        String supplierId = request.getParameter("supplierId");
        String name = request.getParameter("supplierName");
        String addr = request.getParameter("address");
        String phone = request.getParameter("phone");

        ResultInfo info = new ResultInfo();
        if(!oldSupplierId.equals(supplierId)) {
            if(!oldSupplierId.equals(supplierId) && service.isIdExist(supplierId)) {
                info.setFlag(false);
                info.setErrorMsg("该供应商ID已经被使用，请更换一个");
            } else if(service.isUsed(oldSupplierId)) {
                info.setFlag(false);
                info.setErrorMsg("供应商ID被引用，无法修改");
            }
        }
        else {
            if (service.edit(oldSupplierId, supplierId, name, addr, phone)){
                info.setFlag(true);
            }else{
                info.setFlag(false);
                info.setErrorMsg("未知错误");
            }
        }
        writeValue(info, response);
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String supplierId = request.getParameter("supplierId");
        ResultInfo info = new ResultInfo();
        if(service.isUsed(supplierId)) {
            info.setFlag(false);
            info.setErrorMsg("该供应商ID已经被使用，请更换一个");
        }else {
            if(service.delete(supplierId)) {
                info.setFlag(true);
            }else {
                info.setFlag(false);
                info.setErrorMsg("未知错误");
            }
        }
        writeValue(info,response);
    }
}
