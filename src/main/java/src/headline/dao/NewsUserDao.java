package src.headline.dao;


import src.headline.pojo.NewsUser;

public interface NewsUserDao {
    NewsUser findByUid(Integer uid);

    NewsUser findByUsername(String username);

    int regist(NewsUser newsUser);
}
