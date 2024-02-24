package src.headline.service.impl;



import src.headline.dao.NewsTypeDao;
import src.headline.dao.impl.NewsTypeDaoImpl;
import src.headline.pojo.NewsType;
import src.headline.service.NewsTypeService;

import java.util.List;

public class NewsTypeServiceImpl implements NewsTypeService {
    private NewsTypeDao typeDao=new NewsTypeDaoImpl();
    @Override
    public List<NewsType> findAll() {
        return typeDao.findAll();
    }
}
