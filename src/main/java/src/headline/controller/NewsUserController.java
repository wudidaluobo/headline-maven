package src.headline.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import src.headline.common.Result;
import src.headline.common.ResultCodeEnum;
import src.headline.pojo.NewsUser;
import src.headline.service.NewsUserService;
import src.headline.service.impl.NewsUserServiceImpl;
import src.headline.util.JwtHelper;
import src.headline.util.MD5Util;
import src.headline.util.WebUtil;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/user/*")
public class NewsUserController extends BaseController{

    private NewsUserService userService=new NewsUserServiceImpl();
    /**
     *客户端发送请求，后端根据请求头中的token信息确定是否已经登录成功，进而判断是否响应对应的用户信息
     */
    protected void getUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token=req.getHeader("token");
        Result result= Result.build(null, ResultCodeEnum.NOTLOGIN);
        if(token!=null&&!token.equals("")) {
            NewsUser user = userService.findByUid(JwtHelper.getUserId(token).intValue());
            if(user!=null)
            {
                HashMap data=new HashMap<>();
                user.setUserPwd("");
                data.put("loginUser",user);
                result=Result.ok(data);
            }
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     * 登录操作接口，像前端响应一个token作为密钥，用于查看用户信息，监督登录状态，进行权限限制
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewsUser paramUser=WebUtil.readJson(req, NewsUser.class);
        NewsUser loginUser=userService.findByUsername(paramUser.getUsername());
        Result result=null;
        if(loginUser==null)
        {
            result=Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }else if(!MD5Util.encrypt(paramUser.getUserPwd()).equalsIgnoreCase(loginUser.getUserPwd()))
        {
            result=Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }else{
            HashMap data=new HashMap<>();
            data.put("token",JwtHelper.createToken(loginUser.getUid().longValue()));
            result=Result.ok(data);
        }
        WebUtil.writeJson(resp,result);
    }

    protected void checkUsernameUsed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name=req.getParameter("username");
        NewsUser user=userService.findByUsername(name);
        Result result=Result.ok(null);
        if (user!=null)
            result=Result.build(null, ResultCodeEnum.USERNAME_USED);
        WebUtil.writeJson(resp,result);
    }

    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewsUser newsUser=WebUtil.readJson(req,NewsUser.class);
        int rows=userService.regist(newsUser);
        Result result;
        if (rows<1)
            result=Result.build(null,ResultCodeEnum.USERNAME_USED);
        else
            result=Result.ok(null);
        WebUtil.writeJson(resp,result);
    }

    protected void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token=req.getHeader("token");
        Result result=Result.build(null,ResultCodeEnum.NOTLOGIN);
        if(token!=null&&!JwtHelper.isExpiration(token))
        {
            result=Result.ok(null);
        }
        WebUtil.writeJson(resp,result);
    }
}
