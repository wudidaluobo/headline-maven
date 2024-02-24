package src.headline.service;


import src.headline.pojo.NewsUser;

public interface NewsUserService {
    NewsUser findByUid(Integer uid);

    NewsUser findByUsername(String username);

    int regist(NewsUser newsUser);
}
