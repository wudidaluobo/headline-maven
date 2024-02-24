package src.headline.dao;



import src.headline.pojo.NewsType;

import java.util.List;

public interface NewsTypeDao {
    List<NewsType> findAll();
}
