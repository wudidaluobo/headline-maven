package src.headline.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class JdbcUtil {

    private static DataSource dataSource=null;
    private static ThreadLocal<Connection>threadLocal=new ThreadLocal<>();
    static {
        Properties properties=new Properties();
        InputStream ips= JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            properties.load(ips);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            dataSource=DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception
    {
        Connection connection=threadLocal.get();
        if(threadLocal.get()==null)
        {
            connection=dataSource.getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }

    public static void freeConnection() throws  Exception
    {
        if(threadLocal.get()!=null) {
           Connection connection=threadLocal.get();
           connection.setAutoCommit(true);
           threadLocal.remove();
           connection.close();
        }
    }
}
