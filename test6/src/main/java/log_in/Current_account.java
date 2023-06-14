package log_in;

import java.util.LinkedList;
import java.util.List;

///储存当前账户的信息，方便sql的执行
public class Current_account {
    //设置字段
    public static String account = null;
    public static String pwd = null;
    public static int id;
    public static int score;

    public static List<String> record_List = new LinkedList<String>();

    public static List<String> highest = null;
}
