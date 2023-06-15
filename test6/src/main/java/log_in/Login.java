package log_in;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import org.example.MyGameFrmae; //导入自己写的

public class Login extends JFrame implements ActionListener {

    //显示时间
    //账号和密码
    JLabel label_Count = new JLabel("账号:");
    JLabel label_Pwd = new JLabel("密码:");
    JTextField jt_Count = new JTextField();
    JPasswordField jt_Pwd = new JPasswordField();

    //登录按钮下面的
    JLabel label_backPwd = new JLabel("找回密码");
    //JLabel label_signIn = new JLabel("注册账号");
    JButton jb_singIn = new JButton("注册账号");
    JButton jb_record = new JButton("历史记录");
    JButton jb_leaderBoard = new JButton("排行榜");

    JPanel tmp = null;

    //定义按钮
    //JButton jb_Null = new JButton("清零");
    public JButton jb_Start = new JButton("登录");
    JButton jb_Try = new JButton("匿名登录");

    public  Login() {
        super("飞机大战");
        Container c = this.getContentPane();    //创建容器

        tmp = getJButton();
        //c.add(getJButton());
        c.add(tmp);

        //创建计时器线程


        this.setSize(400, 300);
        this.setUndecorated(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        new Login();
    }


    public JPanel getJButton()  {
        JPanel jP = new JPanel();
        jP.setOpaque(false);
        jP.setLayout(null);// 设置空布局，即绝对布局


        //登录按钮
        jb_Start.setBounds(150, 180, 100, 30);
        jP.add(jb_Start);
        jb_Start.addActionListener(this);	//添加监听

        //历史记录按钮
        jb_record.setBounds(100, 230, 80, 20);
        jb_record.setFont(new Font("宋体", Font.PLAIN, 10));
        jb_record.addActionListener(this);
        jP.add(jb_record);

        //排行榜
        jb_leaderBoard.setBounds(225, 230, 80, 20);
        jb_leaderBoard.setFont(new Font("宋体", Font.PLAIN, 10));
        jb_leaderBoard.addActionListener(this);
        jP.add(jb_leaderBoard);

        //这个是中间的文本
        //账号
        label_Count.setBounds(80, 10, 100, 50);
        label_Count.setFont(new Font("宋体", Font.BOLD, 20));
        jP.add(label_Count);

        jt_Count.setBounds(130, 20, 180, 25);
        jP.add(jt_Count);

        //密码
        label_Pwd.setBounds(80, 100, 100, 50);
        label_Pwd.setFont(new Font("宋体", Font.BOLD, 20));
        jP.add(label_Pwd);

        jt_Pwd.setBounds(130, 110, 180, 25);
        jP.add(jt_Pwd);

        //下面小字
        //注册按钮
        jb_singIn.setBounds(160, 150, 80, 20);
        jb_singIn.setFont(new Font("宋体", Font.PLAIN, 10));
        jb_singIn.addActionListener(this);
        jP.add(jb_singIn);

        return jP;
    }

    //登录前判断有没有该用户
    public boolean if_Exist(){

        return true;
    }


    //接口ActionListener的方法重写
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(jb_Start)) {	//登录按钮
//            jt_Count.setText("ui");
//            jt_Pwd.setText("ui");
            int status = Sql.if_Exist(jt_Count.getText(), jt_Pwd.getText());  //(0是没有这个账号，1是有账号但是密码不对，2是可以的)
            if (status == 0){
                JOptionPane.showMessageDialog(tmp, "您还没有注册账号，请先注册", "弹出窗口", JOptionPane.INFORMATION_MESSAGE);
            } else if (status == 1) {
                JOptionPane.showMessageDialog(tmp, "密码错误", "弹出窗口", JOptionPane.INFORMATION_MESSAGE);
            } else {
                MyGameFrmae frame = new MyGameFrmae();

                frame.launchFrame();

                jb_Start.setText("再来一局！");

                //创建表
                Current_account.account = jt_Count.getText();   //记录账号，方便记录游戏

            }

        } else if (e.getSource().equals(jb_singIn)) {   //注册按钮
            Sign_in si = new Sign_in();
            si.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } else if (e.getSource().equals(jb_record)) {   //查询记录的按钮
            Record record = new Record();
            record.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } else if (e.getSource().equals(jb_leaderBoard)) {  //排行榜的按钮
            Best_record bRecord = new Best_record();
            bRecord.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }

}
