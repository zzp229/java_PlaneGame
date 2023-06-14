package log_in;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Best_record extends JFrame {
    //显示时间
    //账号和密码

    JTextArea jTextArea = new JTextArea();



    //面板
    JPanel panel = null;

    //容器
    Container c = null;



    //滚动条
    JScrollPane scrollPane = null;

    public  Best_record() {
        super("历史战绩");
        this.setDefaultCloseOperation(2);

        c = this.getContentPane();    //创建容器

        panel = getJButton();
        c.add(panel);


        this.setSize(400, 300);
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

        //文本框
        jTextArea.setBounds(100, 45, 200, 300);
        jTextArea.setFont(new Font("宋体", Font.BOLD, 13));
        jP.add(jTextArea);

        return jP;
    }

    public void init(){
        jTextArea.setText(null);
        jTextArea.setText("用户" + "\t\t" + "最高分\n");


        Sql.best_record();  //调用这个将最高分加载到List集合中

        //把集合的内容添加到jTextArea
        for(String item : Current_account.highest){
            this.jTextArea.append(item + "\n");
        }

        //设置滚动条
        scrollPane = new JScrollPane(jTextArea);
        scrollPane.setBounds(100, 45, 200, 300);
        scrollPane.setFont(new Font("宋体", Font.BOLD, 13));
        panel.add(scrollPane);
    }

}
