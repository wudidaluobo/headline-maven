package src.headline.service.impl;



import src.headline.dao.NewsHeadlineDao;
import src.headline.dao.impl.NewsHeadlineDaoImpl;
import src.headline.pojo.NewsHeadline;
import src.headline.pojo.vo.HeadlineDetailVo;
import src.headline.pojo.vo.HeadlinePageVo;
import src.headline.pojo.vo.HeadlineQueryVo;
import src.headline.service.NewsHeadlineService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsHeadlineServiceImpl implements NewsHeadlineService {
    NewsHeadlineDao headlineDao= (NewsHeadlineDao) new NewsHeadlineDaoImpl();

    @Override
    public int removeByHid(int hid) {
        return headlineDao.removeByHid(hid);
    }

    public Map findPage(HeadlineQueryVo headlineQuery) {
        Map pageInfo=new HashMap();
        int pageNum=headlineQuery.getPageNum();
        int pageSize=headlineQuery.getPageSize();
        int totalSize=headlineDao.findPageCount(headlineQuery);
        int totalPage=totalSize%pageSize==0?totalSize/pageSize:totalSize/pageSize+1;
        List<HeadlinePageVo> pageData=headlineDao.findPageList(headlineQuery);
        pageInfo.put("pageData",pageData);
        pageInfo.put("totalPage",totalPage);
        pageInfo.put("totalSize",totalSize);
        pageInfo.put("pageSize",pageSize);
        pageInfo.put("pageNum",pageNum);
        return pageInfo;
    }

    @Override
    public HeadlineDetailVo findHeadlineDetail(int hid) {
        headlineDao.incrPageViews(hid);
        return headlineDao.findHeadlineDetail(hid);
    }

    @Override
    public void addNewHeadline(NewsHeadline newsHeadline) {
        headlineDao.addNewHeadline(newsHeadline);
    }

    @Override
    public NewsHeadline findHeadlineByHid(int hid) {
        return headlineDao.findHeadlineByHid(hid);
    }

    @Override
    public int update(NewsHeadline headline) {
        return headlineDao.update(headline);
    }
}
