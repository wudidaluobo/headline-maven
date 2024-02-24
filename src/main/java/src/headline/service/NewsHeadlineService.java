package src.headline.service;



import src.headline.pojo.NewsHeadline;
import src.headline.pojo.vo.HeadlineDetailVo;
import src.headline.pojo.vo.HeadlineQueryVo;

import java.util.Map;

public interface NewsHeadlineService {
    Map findPage(HeadlineQueryVo headlineQuery);

    HeadlineDetailVo findHeadlineDetail(int hid);

    void addNewHeadline(NewsHeadline newsHeadline);

    NewsHeadline findHeadlineByHid(int hid);

    int update(NewsHeadline headline);

    int removeByHid(int hid);
}
