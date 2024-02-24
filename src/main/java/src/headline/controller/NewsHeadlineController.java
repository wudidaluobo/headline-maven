package src.headline.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import src.headline.common.Result;
import src.headline.pojo.NewsHeadline;
import src.headline.service.NewsHeadlineService;
import src.headline.service.impl.NewsHeadlineServiceImpl;
import src.headline.util.JwtHelper;
import src.headline.util.WebUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/headline/*")
public class NewsHeadlineController extends BaseController{


     private NewsHeadlineService headlineService=new NewsHeadlineServiceImpl();
    protected void publish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewsHeadline newsHeadline= WebUtil.readJson(req, NewsHeadline.class);
        newsHeadline.setPublisher(JwtHelper.getUserId(req.getHeader("token")).intValue());
        headlineService.addNewHeadline(newsHeadline);
        WebUtil.writeJson(resp, Result.ok(null));
    }


    protected void findHeadlineByHid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int hid=Integer.parseInt(req.getParameter("hid"));
        NewsHeadline headline=headlineService.findHeadlineByHid(hid);
        Map data=new HashMap<>();
        data.put("headline",headline);
        WebUtil.writeJson(resp,Result.ok(data));
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         NewsHeadline headline=WebUtil.readJson(req,NewsHeadline.class);
         int rows=headlineService.update(headline);
         Result result=null;
         if(rows>0)
             result=Result.ok(null);
         WebUtil.writeJson(resp,result);
    }

    protected void removeByHid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
              int hid=Integer.parseInt(req.getParameter("hid"));
              int rows=headlineService.removeByHid(hid);
              Result result=null;
              if(rows>0)
                  result=Result.ok(null);
              WebUtil.writeJson(resp,result);
    }
}
