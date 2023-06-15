package log_in;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class Sign_in extends JFrame implements ActionListener {
    //显示时间
    //账号和密码
    JLabel label_Count = new JLabel("请输入账号:");
    JLabel label_Pwd = new JLabel("请输入密码:");
    JTextField jt_Count = new JTextField();
    JPasswordField jt_Pwd = new JPasswordField();

    //定义按钮
    JButton jb_SingIn = new JButton("注册");

    //panel面板
    JPanel jPanel = null;

    public  Sign_in() {
        super("账号注册");
        Container c = this.getContentPane();    //创建容器

        jPanel = getJButton();  //添加好控件的面板

        c.add(jPanel);

        this.setSize(400, 300);
        this.setUndecorated(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clear_TextField();  //初始化文本框
    }


//    public static void main(String[] args) {
//        new Login();
//    }


    //初始化输入框
    public void clear_TextField(){
        this.jt_Count.setText(null);
        this.jt_Pwd.setText(null);
    }

    public JPanel getJButton()  {
        JPanel jP = new JPanel();
        jP.setOpaque(false);
        jP.setLayout(null);// 设置空布局，即绝对布局

        //登录按钮
        jb_SingIn.setBounds(150, 180, 100, 30);
        jP.add(jb_SingIn);
        jb_SingIn.addActionListener(this);	//添加监听


        //这个是中间的文本
        //账号
        label_Count.setBounds(60, 10, 100, 50);
        label_Count.setFont(new Font("宋体", Font.BOLD, 13));
        jP.add(label_Count);

        jt_Count.setBounds(140, 20, 180, 25);
        jP.add(jt_Count);


        //密码
        label_Pwd.setBounds(60, 100, 100, 50);
        label_Pwd.setFont(new Font("宋体", Font.BOLD, 13));
        jP.add(label_Pwd);

        jt_Pwd.setBounds(140, 110, 180, 25);
        jP.add(jt_Pwd);

        return jP;
    }

    //判断账户和密码的长度是否合法
    private boolean check(){
        //是否为空
        if (this.jt_Count.getText().length() == 0 || this.jt_Pwd.getText().length() == 0) {
            JOptionPane.showMessageDialog(jPanel, "账号和密码不能为空", "注册失败", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        //验证长度
        if (this.jt_Count.getText().length() > 20 || this.jt_Pwd.getText().length() > 20) {
            JOptionPane.showMessageDialog(jPanel, "账号和密码的长度不能超过20", "注册失败", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        String tmp = "^[A-Za-z0-9]+$";
        //正则表达式限定字母和数字，这个要完善验证只有密码和数字
        if (Pattern.matches(tmp ,jt_Count.getText()) == false || Pattern.matches(tmp, jt_Pwd.getText()) == false ){
            JOptionPane.showMessageDialog(jPanel, "账号和密码必须是数字和字母组成", "注册错误", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        //验证这个用户
        if (Sql.signIn(jt_Count.getText(), jt_Pwd.getText()) == false) {
            JOptionPane.showMessageDialog(jPanel, "这个账号已被注册，请重新注册", "注册错误", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    //接口ActionListener的方法重写
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(jb_SingIn)) {	//注册按钮

            if (check() == false) {
                 //将文本框初始化
                clear_TextField();
            } else {
                //创建这个表的时候要看看有没有这个用户先
                Sql.create_table("a"+jt_Count.getText()); //如果是可以注册的话就给这个用户创建一张记录表
                this.dispose(); //关闭这个窗口
            }

        }
    }

}
