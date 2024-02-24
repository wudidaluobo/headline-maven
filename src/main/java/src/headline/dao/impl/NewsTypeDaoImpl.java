package src.headline.dao.impl;


import src.headline.dao.BaseDao;
import src.headline.dao.NewsTypeDao;
import src.headline.pojo.NewsType;

import java.util.List;

public class NewsTypeDaoImpl extends BaseDao implements NewsTypeDao {
    @Override
    public List<NewsType> findAll() {
        String sql="select tid,tname from news_type";
        return baseQuery(NewsType.class,sql);
    }
}
