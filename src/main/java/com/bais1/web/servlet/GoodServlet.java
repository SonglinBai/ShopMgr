package com.bais1.web.servlet;


import com.bais1.domain.*;
import com.bais1.service.GoodService;
import com.bais1.service.UserService;
import com.bais1.service.impl.GoodServiceImpl;
import com.bais1.service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@WebServlet("/good/*")
public class GoodServlet extends BaseServlet {
    private GoodService goodService = new GoodServiceImpl();

    /**
     * 返回pageQuery
     *
     * @param request
     * @param response
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //1.接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String categoryId = request.getParameter("categoryId");
        String supplierId = request.getParameter("supplierId");
        String goodName = request.getParameter("goodName");
        String goodId = request.getParameter("goodId");

        int currentPage = 0;//当前页码，如果不传递，则默认为第一页
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        int pageSize = 0;//每页显示条数，如果不传递，默认每页显示5条记录
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }

        //3. 调用service查询PageBean对象
        PageBean<Good> pb = goodService.pageQuery(goodId, goodName, categoryId, supplierId, currentPage, pageSize);

        //4. 将pageBean对象序列化为json，返回
        writeValue(pb, response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取参数
        String ogidStr = request.getParameter("ogid");
        String gidStr = request.getParameter("gid");
        String name = request.getParameter("name");
        String cidStr = request.getParameter("cid");
        String description = request.getParameter("description");
        String supplierId = request.getParameter("supplierId");
        String retailPriceStr = request.getParameter("retailPrice");
        String purchasePriceStr = request.getParameter("purchasePrice");

        GoodStatus status = GoodStatus.ENABLE;

        float retailPrice = 0;
        if (retailPriceStr!=null && retailPriceStr.length()>0)
            retailPrice = Float.parseFloat(retailPriceStr);

        float purchasePrice = 0;
        if (purchasePriceStr!=null && retailPriceStr.length()>0)
            purchasePrice = Float.parseFloat(purchasePriceStr);

        Good good = new Good(gidStr, name, cidStr, retailPrice, purchasePrice, supplierId, status, description);
        ResultInfo info = new ResultInfo();
        if (!ogidStr.equals(gidStr)&&goodService.isIdExist(good)) {
            info.setFlag(false);
            info.setErrorMsg("改商品id已经存在");
        } else {
            if (goodService.edit(ogidStr, good)) {
                info.setFlag(true);
            } else {
                info.setFlag(false);
                info.setErrorMsg("未知错误");
            }
        }
        writeValue(info, response);
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String gidStr = request.getParameter("gid");

        ResultInfo info = new ResultInfo();

        if(goodService.isUsed(gidStr)){
            info.setFlag(false);
            info.setErrorMsg("该商品已经被使用了，无法删除");
        }else {
            if(goodService.deleteById(gidStr)){
                info.setFlag(true);
            }else {
                info.setFlag(false);
                info.setErrorMsg("未知错误");
            }
        }

        writeValue(info,response);
    }

    public void deleteByArray(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] gidStr = request.getParameterValues("gid");
        boolean flag = true;
        ArrayList<String> gids = new ArrayList<>();
        for (String value : gidStr) {
            if(goodService.isUsed(value)){
                flag = false;
                break;
            }
            gids.add(value);
        }
        ResultInfo info = new ResultInfo();
        if(!flag){
            info.setFlag(false);
            info.setErrorMsg("有商品已经被使用，无法删除");
        }
        else {
            if(goodService.deleteByArray(gids)){
                info.setFlag(true);
            }else {
                info.setFlag(false);
                info.setErrorMsg("未知错误");
            }
        }
        writeValue(info,response);
    }

    public void getById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String gidStr = request.getParameter("gid");
        Good good = goodService.getById(gidStr);
        writeValue(good, response);
    }

//    public void getNextId(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        writeValue(goodService.getNextId(), response);
//    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String gidStr = request.getParameter("gid");
        String name = request.getParameter("name");
        String cidStr = request.getParameter("cid");
        String supplierId = request.getParameter("supplierId");
        String description = request.getParameter("description");
        String retailPriceStr = request.getParameter("retailPrice");
        String purchasePriceStr = request.getParameter("purchasePrice");

        if(cidStr.length()==0) {
            cidStr=null;
        }
        if(supplierId.length()==0) {
            supplierId=null;
        }

        float retailPrice = 0;
        if (retailPriceStr!=null && retailPriceStr.length()>0)
            retailPrice = Float.parseFloat(retailPriceStr);

        float purchasePrice = 0;
        if (purchasePriceStr!=null && retailPriceStr.length()>0)
            purchasePrice = Float.parseFloat(purchasePriceStr);

        Good good = new Good(gidStr, name, cidStr, retailPrice, purchasePrice, supplierId, GoodStatus.ENABLE, description);
        ResultInfo info = new ResultInfo();
        if (goodService.isIdExist(good)) {
            info.setFlag(false);
            info.setErrorMsg("该商品id已经存在");
        } else {
            if (goodService.create(good)){
                info.setFlag(true);
            }else{
                info.setFlag(false);
                info.setErrorMsg("未知错误");
            }
        }
        writeValue(info, response);
    }

    public void purchase(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        Enumeration<String> gids = request.getParameterNames();
        Map<String, Integer> map = new HashMap<>();
        float realPrice=0;
        String note=null;

        String amountStr;
        String gidStr;

        while (gids.hasMoreElements()) {
            gidStr = (String) gids.nextElement();
            if(gidStr.equals("realPrice")){
                realPrice=Float.parseFloat(request.getParameter(gidStr));
                continue;
            }else if(gidStr.equals("note")) {
                note=request.getParameter(gidStr);
                continue;
            }
            amountStr = request.getParameter(gidStr);

            if (amountStr != null || amountStr.length() > 0)
                map.put(gidStr, Integer.parseInt(amountStr));
            else continue;
        }
        goodService.purchaseGood(map,realPrice,note, user);
    }

    public void sale(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        Enumeration<String> gids = request.getParameterNames();
        Map<String, Integer> map = new HashMap<>();
        float realPrice=0;
        String note = null;

        String amountStr;
        String gidStr;

        while (gids.hasMoreElements()) {
            gidStr = (String) gids.nextElement();
            if(gidStr.equals("realPrice")){
                realPrice=Float.parseFloat(request.getParameter(gidStr));
                continue;
            }else if(gidStr.equals("note")) {
                note=request.getParameter(gidStr);
                continue;
            }
            amountStr = request.getParameter(gidStr);
            if (amountStr != null || amountStr.length() > 0)
                map.put(gidStr, Integer.parseInt(amountStr));
            else continue;
        }
        goodService.saleGood(map,realPrice,note, user);
    }

    public void getByArray(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] gidStr = request.getParameterValues("gid");
        ArrayList<String> gids = new ArrayList<>();
        for (String value : gidStr) {
            gids.add(value);
        }

        List<Good> goods = goodService.getByArray(gids);

        writeValue(goods, response);
    }
}
