package src.headline.service.impl;


import src.headline.dao.NewsUserDao;
import src.headline.dao.impl.NewsUserDaoImpl;
import src.headline.pojo.NewsUser;
import src.headline.service.NewsUserService;
import src.headline.util.MD5Util;

public class NewsUserServiceImpl implements NewsUserService {
    private NewsUserDao userDao= (NewsUserDao) new NewsUserDaoImpl();
    @Override
    public NewsUser findByUid(Integer uid) {
        return userDao.findByUid(uid);
    }

    @Override
    public NewsUser findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public int regist(NewsUser newsUser) {
        newsUser.setUserPwd(MD5Util.encrypt(newsUser.getUserPwd()));
        return userDao.regist(newsUser);
    }
}
