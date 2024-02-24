package src.headline.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import src.headline.common.Result;
import src.headline.pojo.NewsType;
import src.headline.pojo.vo.HeadlineDetailVo;
import src.headline.pojo.vo.HeadlineQueryVo;
import src.headline.service.NewsHeadlineService;
import src.headline.service.NewsTypeService;
import src.headline.service.impl.NewsHeadlineServiceImpl;
import src.headline.service.impl.NewsTypeServiceImpl;
import src.headline.util.WebUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/portal/*")
public class PortalController extends BaseController{
    private NewsTypeService typeService=new NewsTypeServiceImpl();
    private NewsHeadlineService headlineService=new NewsHeadlineServiceImpl();
    protected void findAllTypes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<NewsType> typeList=typeService.findAll();
        WebUtil.writeJson(resp, Result.ok(typeList));
    }

    protected void findNewsPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HeadlineQueryVo headlineQuery=WebUtil.readJson(req, HeadlineQueryVo.class);
        Map pageInfo=headlineService.findPage(headlineQuery);
        Map data=new HashMap<>();
        data.put("pageInfo",pageInfo);
        Result result=Result.ok(data);
        WebUtil.writeJson(resp,result);
    }

    protected void showHeadlineDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int hid=Integer.parseInt(req.getParameter("hid"));
        HeadlineDetailVo headlineDetailVo=headlineService.findHeadlineDetail(hid);
        Map data=new HashMap<>();
        data.put("headline",headlineDetailVo);
        WebUtil.writeJson(resp,Result.ok(data));
    }
}
