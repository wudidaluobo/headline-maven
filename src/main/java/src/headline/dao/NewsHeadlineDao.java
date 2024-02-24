package src.headline.dao;



import src.headline.pojo.NewsHeadline;
import src.headline.pojo.vo.HeadlineDetailVo;
import src.headline.pojo.vo.HeadlinePageVo;
import src.headline.pojo.vo.HeadlineQueryVo;

import java.util.List;

public interface NewsHeadlineDao {
    int findPageCount(HeadlineQueryVo headlineQuery);

    List<HeadlinePageVo> findPageList(HeadlineQueryVo headlineQuery);

    HeadlineDetailVo findHeadlineDetail(int hid);

    int incrPageViews(int hid);

    void addNewHeadline(NewsHeadline newsHeadline);

    NewsHeadline findHeadlineByHid(int hid);

    int update(NewsHeadline headline);

    int removeByHid(int hid);
}
