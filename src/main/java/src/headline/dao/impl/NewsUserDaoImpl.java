package src.headline.dao.impl;


import src.headline.dao.BaseDao;
import src.headline.dao.NewsUserDao;
import src.headline.pojo.NewsUser;

import java.util.List;

public class NewsUserDaoImpl extends BaseDao implements NewsUserDao {
    @Override
    public NewsUser findByUid(Integer uid) {
        String sql="select uid,username,user_pwd as userPwd,nick_name as nickName from news_user where uid=?";
        List<NewsUser> newsUserList=baseQuery(NewsUser.class,sql,uid);
        return newsUserList!=null&&newsUserList.size()>0?newsUserList.get(0):null;
    }

    @Override
    public NewsUser findByUsername(String username) {
        String sql="select uid,username,user_pwd as userPwd,nick_name as nickName from news_user where username=?";
        List<NewsUser> newsUserList=baseQuery(NewsUser.class,sql,username);
        return newsUserList!=null&&newsUserList.size()>0?newsUserList.get(0):null;
    }

    @Override
    public int regist(NewsUser newsUser) {
       String sql= """
               insert into news_user values(DEFAULT,?,?,?)
               """;
       return executeUpdate(sql,newsUser.getUsername(),newsUser.getUserPwd(),newsUser.getNickName());
    }
}
