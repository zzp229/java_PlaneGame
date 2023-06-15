package log_in;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Record extends JFrame implements ActionListener {
    //显示时间
    //账号和密码
    JLabel label_Count = new JLabel("输入查询账号:");
    JTextArea jTextArea = new JTextArea();

    JTextField jt_Count = new JTextField();

    //面板
    JPanel panel = null;

    //容器
    Container c = null;

    //定义按钮
    JButton jb_Search = new JButton("查询");

    //滚动条
    JScrollPane scrollPane = null;

    public  Record() {
        super("历史战绩");
        this.setDefaultCloseOperation(2);

        c = this.getContentPane();    //创建容器

        panel = getJButton();
        c.add(panel);


        this.setSize(400, 450);
        this.setUndecorated(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }



    public JPanel getJButton()  {
        JPanel jP = new JPanel();
        jP.setOpaque(false);
        jP.setLayout(null);// 设置空布局，即绝对布局

        //查询按钮
        jb_Search.setBounds(280, 15, 100, 30);
        jP.add(jb_Search);
        jb_Search.addActionListener(this);	//添加监听


        //输入账号
        label_Count.setBounds(0, 10, 100, 50);
        label_Count.setFont(new Font("宋体", Font.BOLD, 13));
        jP.add(label_Count);

        jt_Count.setBounds(100, 20, 170, 25);
        jP.add(jt_Count);

        //文本框
        jTextArea.setBounds(100, 45, 200, 300);
        jTextArea.setFont(new Font("宋体", Font.BOLD, 13));
        jP.add(jTextArea);

        return jP;
    }

    public void init(){
        jTextArea.setText(null);
        jt_Count.setText(null);
        jTextArea.setText("分数     在线时间\n");
    }

    //接口ActionListener的方法重写
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(jb_Search)) {	//查询按钮

           if (Sql.search_Record(jt_Count.getText()) == false) {
               //提示没有这个用户
               JOptionPane.showMessageDialog(panel, "没有该用户信息", "null", JOptionPane.INFORMATION_MESSAGE);
           } else {
               for(String value : Current_account.record_List){
                   jTextArea.append(value + "\n");
               }
               //添加到滚动条
               scrollPane = new JScrollPane(jTextArea);
                scrollPane.setBounds(100, 45, 200, 300);
                scrollPane.setFont(new Font("宋体", Font.BOLD, 13));
                panel.add(scrollPane);
           }
        }
    }


}

