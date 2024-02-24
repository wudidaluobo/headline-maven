package src.headline.dao.impl;



import src.headline.dao.BaseDao;
import src.headline.dao.NewsHeadlineDao;
import src.headline.pojo.NewsHeadline;
import src.headline.pojo.vo.HeadlineDetailVo;
import src.headline.pojo.vo.HeadlinePageVo;
import src.headline.pojo.vo.HeadlineQueryVo;

import java.util.ArrayList;
import java.util.List;

public class NewsHeadlineDaoImpl extends BaseDao implements NewsHeadlineDao {


    @Override
    public int removeByHid(int hid) {
        String sql="update news_headline set is_deleted=1 where hid=?";
        return executeUpdate(sql,hid);
    }

    @Override
    public List<HeadlinePageVo> findPageList(HeadlineQueryVo headlineQueryVo) {
        List params = new ArrayList();

        String sql  = """
                select 
                    hid ,
                    title,
                    type,
                    page_views pageViews,
                    TIMESTAMPDIFF(HOUR,create_time,now()) pastHours ,
                    publisher 
                from 
                    news_headline
                where 
                    is_deleted = 0
                    
                """;
        if (headlineQueryVo.getType() != 0 ){
            sql = sql.concat(" and type = ? ");
            params.add(headlineQueryVo.getType());

        }
        if(headlineQueryVo.getKeyWords() != null && !headlineQueryVo.getKeyWords().equals("")){
            sql = sql.concat(" and title like ? ");
            params.add("%"+headlineQueryVo.getKeyWords()+"%");
        }

        sql=sql.concat(" order by pastHours ASC , page_views DESC ");
        sql=sql.concat(" limit ?,? ");
        params.add((headlineQueryVo.getPageNum()-1)*headlineQueryVo.getPageSize());
        params.add(headlineQueryVo.getPageSize());

        return baseQuery(HeadlinePageVo.class,sql,params.toArray());
    }

    @Override
    public int findPageCount(HeadlineQueryVo headlineQueryVo) {
        List params = new ArrayList();

        String sql  = """
                select 
                    count(1)
                from 
                    news_headline
                where 
                    is_deleted = 0
                    
                    
                """;
        if (headlineQueryVo.getType() != 0 ){
            sql = sql.concat(" and type = ? ");
            params.add(headlineQueryVo.getType());

        }
        if(headlineQueryVo.getKeyWords() != null && !headlineQueryVo.getKeyWords().equals("")){
            sql = sql.concat(" and title like ? ");
            params.add("%"+headlineQueryVo.getKeyWords()+"%");
        }

        Long count = baseQueryObject(Long.class, sql, params.toArray());
        return count.intValue();
    }

    @Override
    public int incrPageViews(int hid) {
        String sql="update news_headline set page_views=page_views+1 where hid=?";
        return executeUpdate(sql,hid);
    }

    @Override
    public HeadlineDetailVo findHeadlineDetail(int hid) {
        String sql= """
                select 
                h.hid,
                h.title,
                h.article,
                h.type type,
                h.publisher,
                h.page_views pageViews,
                TIMESTAMPDIFF(HOUR,h.create_time,now()) pastHours,
                u.nick_name author,
                t.tname typeName
                from news_headline h 
                left join news_type t
                on h.type=t.tid
                left join news_user u
                on h.publisher=u.uid
                where h.is_deleted=0 and h.hid=?
                  
                """;
        List<HeadlineDetailVo> list=baseQuery(HeadlineDetailVo.class,sql,hid);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public void addNewHeadline(NewsHeadline newsHeadline) {
        String sql= """
                insert into news_headline
                values(DEFAULT,?,?,?,?,0,now(),now(),0)
                """;
        executeUpdate(sql,newsHeadline.getTitle(),newsHeadline.getArticle(),newsHeadline.getType(),newsHeadline.getPublisher());
    }

    @Override
    public NewsHeadline findHeadlineByHid(int hid) {
        String sql= """
                select 
                hid,
                title,
                article,
                type,
                publisher,
                create_time createTime,
                update_time updateTime,
                is_deleted isDeleted
                from news_headline
                where hid=?
                """;
        List<NewsHeadline> list=baseQuery(NewsHeadline.class,sql,hid);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public int update(NewsHeadline headline) {
        String sql= """
                update 
                news_headline set 
                title=?,
                article=?,
                type=?,
                update_time=now()
                where hid=?
                """;
        return  executeUpdate(sql,headline.getTitle(),headline.getArticle(),headline.getType(),headline.getHid());
    }
}
