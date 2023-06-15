package log_in;
import java.sql.*;
import java.util.LinkedList;

public class Sql {

    //注册功能的静态代码块 (0表示注册成功，1表示已经有这个账号了，2表示账号超过20位了，3表示密码超过20位了)
    //false是有这个账户了
    public static boolean signIn(String account, String pwd){
        Connection conn = null;
        Statement stmt = null;

        try {
            // 注册驱动
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            // 具体数据库，需ip、端口、数据库名
            String url="jdbc:mysql://localhost:3306/login";
            //数据库的账号和密码
            String user="root";
            String password="123456";
            conn = DriverManager.getConnection(url,user,password);

            //先判断有没有这个用户先
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from log_in");
            while (rs.next()){
                if ((rs.getString(1)).equals(account)){   //里面有这个账号
                    return false;
                }
            }

            //使用PreparedStatement带参数sql语句编程(课本313页)
            String sql = "insert  into log_in(account, pwd) values (?, ?)"; ////
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, account);
            ptmt.setString(2,pwd);
            //执行sql，返回影响条数
            int count = ptmt.executeUpdate();   //executeUpdate用来执行insert，delete，update

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {  //释放资源
            dispose_Resource(conn, stmt);
        }
        return true;
    }



    //判断表中是否有这个用户(0是没有这个账号，1是有账号但是密码不对，2是可以的)
    public static int if_Exist(String account, String pwd){
        Connection conn = null;
        Statement stmt = null;

        try {
            // 注册驱动
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            // 具体数据库，需ip、端口、数据库名
            String url="jdbc:mysql://localhost:3306/login";
            //数据库的账号和密码
            String user="root";
            String password="123456";
            conn = DriverManager.getConnection(url,user,password);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from log_in");
            while (rs.next()){
                if ((rs.getString(1)).equals(account)){   //里面有这个账号
                    if ((rs.getString(2)).equals(pwd)){   //账号对，密码也对
                        return 2;
                    } else {
                        return 1;   //这里返回的是有账号，密码不对的
                    }
                }
            }

            return 0;   //没账号

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {  //释放资源
            dispose_Resource(conn, stmt);
        }

        return 2;
    }



    //一个用户有一张表来存储记录
    public static boolean create_table(String account){
        Connection conn = null;
        Statement stmt = null;

        try {
            // 注册驱动
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            // 具体数据库，需ip、端口、数据库名
            String url="jdbc:mysql://localhost:3306/login";
            //数据库的账号和密码
            String user="root";
            String password="123456";
            conn = DriverManager.getConnection(url,user,password);
            stmt = conn.createStatement();

            String sql = "CREATE TABLE " + account +
                    "(date DateTime DEFAULT NOW(), " +
                    "score INT)";

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {  //释放资源
            dispose_Resource(conn, stmt);
        }
        return true;
    }

    //记录每次玩的分数（已自动加a）
    public static void record_Score(String account, int score){
        Connection conn = null;
        Statement stmt = null;
        try {
            //注册驱动
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/login";
            conn = DriverManager.getConnection(url, "root", "123456");
            String sql1 = "insert into ?(score) values(4)";
            String sql = "insert into a" + account + "(score) values(?)";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,String.valueOf(score));
            int count = ptmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            dispose_Resource(conn, stmt);
        }
    }

    //使用rs.Next游标的时候要数据连接才行
    public static Boolean search_Record(String account){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/login";
            conn = DriverManager.getConnection(url, "root", "123456");

            //在玩家目录表，判断有没有这个用户
            boolean exit = false;
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from log_in");
            while (rs.next()){
                if ((rs.getString(1)).equals(account)){   //里面有这个账号
                    exit = true;
                }
            }

            if (exit == false) {    //没有这个玩家，返回false
                return false;
            }

            //返回account账户的记录
            rs = stmt.executeQuery("select  * from a" + account + " order by date desc;");    //这个是返回的值
            while (rs.next()){
                Current_account.record_List.add(" "  + rs.getString(2) + "   " + rs.getString(1));
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            dispose_Resource(conn, stmt);
        }
        return true;
    }


    /////这里需要更改
    //这个是获取最高分表
    public static void best_record(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/login";
            conn = DriverManager.getConnection(url, "root", "123456");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from log_in order by bestScore desc"); /////这个增加排序和修改类型后的
            Current_account.highest = new LinkedList<String>(); //初始化最高分集合
            while (rs.next()){
                Current_account.highest.add(rs.getString(1) + "\t\t" + rs.getInt(3));
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            dispose_Resource(conn, stmt);
        }
    }

    //这个是更新最高分表
    public static void update_record(String account){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/login";
            conn = DriverManager.getConnection(url, "root", "123456");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from log_in " + "where account =" + account);
            rs.next();  //移动游标
            String s = rs.getString(3);
            int tmp = Integer.parseInt(s);

            System.out.println("游标的数： " + tmp);
            //如果大的就更新最高分
            if (Current_account.score > tmp) {
                System.out.println("-----------------");
                //使用PreparedStatement带参数sql语句编程
                //update log_in set bestScore=0 where account='4';
                String sql = "update log_in set bestScore = ? where account = ?"; ////
                PreparedStatement ptmt = conn.prepareStatement(sql);
                //ptmt.setString(1, String.valueOf(Current_account.score));
                ptmt.setInt(1, Current_account.score);  /////这里变得插入int类型
                ptmt.setString(2, account);
                //执行sql，返回影响条数
                int count = ptmt.executeUpdate();   //executeUpdate用来执行insert，delete，update
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            dispose_Resource(conn, stmt);
        }
    }



    //释放资源
    public static void dispose_Resource(Connection conn, Statement stmt){
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
