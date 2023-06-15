package org.example;

import log_in.Current_account;
import log_in.Sql;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

import static org.example.GameUtil.*;

public class MyGameFrmae extends Frame implements Runnable {


    Image bg = GameUtil.getImage("090149pidiwsbtszrgglap.jpg");//背景图

    Image plane = GameUtil.getImage("e9cd813a446d4c5d93a5da92f5f1afb4.png");  //飞机图


    Plane plank = new Plane(plane, 140, 350, 8);   ///飞机出场
    //    int x = 200, y = 200;//飞机坐标

    ///敌机一
    private EnemyPlane enemyPlane;
    ///敌机二
    private EnemyPlane enemyPlane2;


    //    Shell shell = new Shell();//一发炮弹
    Shell[] shells = new Shell[50];  ///创建炮弹对象

    Explode explode;  //声明是一个炮弹

    //时间
    Date startTime = new Date();  ///开始时间
    Date endTime;   //结束时间


    //重画线程，帮助我们反复重画窗口
    class PaintThread extends Thread {

        @Override
        public void run() {
            //死循环，一直调用
            while (true) {
                //调用图画
                repaint(); //重画
                //暂停慢一点
                try {
                    Thread.sleep(40);   //1s=1000ms,停顿40毫秒，相当于1s25张左右的图片，也就是飞机灵敏度
                } catch (InterruptedException e) {
                    e.printStackTrace();
//                    throw new RuntimeException(e);
                }
            }
        }
    }


    //初始化窗口
    public void launchFrame() {

        this.setTitle("飞机大战---小游戏");

        this.setVisible(true); //设计窗口可见
        this.setSize(333, 550);  ///游戏窗口初始化大小
        this.setLocation(330, 350);     ///游戏窗口出现位置

        //敌机创建1
        Image enemyImage = GameUtil.getImage("20190310203343399.png");
        enemyPlane = new EnemyPlane(enemyImage, 100, 100, 1);
        //创建线程1
        Thread enemyThread = new Thread(enemyPlane);
        enemyThread.start();

        // 敌机2
        Image enemyImage2 = GameUtil.getImage("20190310203343399.png");
        enemyPlane2 = new EnemyPlane(enemyImage2, 199, 111, 0);
        //创建线程2
        Thread enemyThread2 = new Thread(enemyPlane2);
        enemyThread2.start();


        //窗口监视
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //增加关闭窗口的动作


        //调用图画运动,启动窗口绘制线程
        new PaintThread().start();

        //启动键盘监听,控制飞机移动
        this.addKeyListener(new KeyMOnitor());

        //初始化炮弹
        for (int i = 0; i < shells.length; i++) {
            shells[i] = new Shell();
        }
    }


    //时间改用线程
    //时间
//    public int time_Sendconds = 0;
//    Timer timer = null;

    int period;   //统计时间

    //构造方法初始化，主要是使用线程计数
    public MyGameFrmae() {
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                //System.out.println(new Date(scheduledExecutionTime());
//                time_Sendconds++;
//            }
//        }, 1000);

    }

    boolean is_Pend = true; ////用来记录是否还要继续计时
    //--
    boolean if_Insert = true;
    //背景动画
    @Override
    public void paint(Graphics g) {
        g.drawImage(bg, 0, 0, FRAME_WINDTH, FRAME_HIGHT, null);

        //让敌机一活
        if (enemyPlane.isLive()) {
            enemyPlane.drawMySelf(g);
        }
        //让敌机二活
        if (enemyPlane2.isLive()) {
            enemyPlane2.drawMySelf(g);
        }


        plank.drawMySelf(g);  //飞机动起来

        for (int i = 0; i < shells.length; i++) {

            // 敌机与飞机碰撞
            if (enemyPlane.isLive() || enemyPlane2.isLive()) {
                enemyPlane.drawMySelf(g);
                enemyPlane2.drawMySelf(g);

                // 碰撞检测：判断敌机和飞机是否发生碰撞
                if (plank.getRec().intersects(enemyPlane.getRec()) || plank.getRec().intersects(enemyPlane2.getRec())) {
                    plank.setLive(false); // 停止飞机的移动

                    plank.live = false;

                    int period = (int) ((endTime.getTime() - startTime.getTime()) / 1000);
                    is_Pend = false;

                    // 触发爆炸效果
                    if (explode == null) {
                        explode = new Explode(plank.x, plank.y);
                    }
                    explode.draw(g);   //爆炸效果

                }
            }

            //炮弹与飞机碰撞
            if (shells[i] != null) {
                shells[i].drawMySelf(g);

                ////炮弹检测: 判断炮弹是否与飞机矩形发生碰撞
                boolean pend = shells[i].getRec().intersects(plank.getRec());

                if (is_Pend) {
                    endTime = new Date();   ///结束时间
                }

                if (pend) {  ///碰撞了
                    plank.live = false;

                    int period = (int) ((endTime.getTime() - startTime.getTime()) / 1000);
                    is_Pend = false;

                    //爆炸
                    if (explode == null) {
                        explode = new Explode(plank.x, plank.y);
                    }
                    explode.draw(g);  //爆炸效果
                }
            }
        }

        ////游戏结束时间统计
        if (!plank.live) {  //||!enemyPlane.isLive()||!enemyPlane2.isLive()

            int period = (int) ((endTime.getTime() - startTime.getTime()) / 1000);
            printInfo(g, "生存时间：" + period + "秒", 20, 100, 350, Color.YELLOW);
            printInfo(g, "按‘Esc'退回登录界面重新游戏", 20, 30, 370, Color.YELLOW);
            if (if_Insert == true) {    //--
                Current_account.score = period; //记录分数  /////
                Sql.record_Score(Current_account.account, Current_account.score);   //--
                //System.out.println("类MyGrameFrame" + "账号" + Current_account.account + "   分数" + Current_account.score);
                Sql.update_record(Current_account.account); //把最高分表更新一遍
                if_Insert = false;
            }
        }


    }


    //打印信息：多少秒时间
    public void printInfo(Graphics g, String str, int size, int x, int y, Color color) {///把画笔Graphics g传入

        Font oldFont = g.getFont();
        Color oldColor = g.getColor();  ///方便复原

        Font f = new Font("宋体", Font.BOLD, size);
        g.setFont(f);
        g.setColor(color);
        g.drawString(str, x, y);

        g.setFont(oldFont);
        g.setColor(oldColor);
    }

    //键盘监听内部类,键盘操作
    class KeyMOnitor extends KeyAdapter {

        //按下键
        @Override
        public void keyPressed(KeyEvent e) {

//            System.out.println("你按下键盘了"+e.getKeyCode());
            plank.addDirection(e);

            keyborad_Esc(e);
        }

        //独立窗口
        public void keyborad_Esc(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                dispose();

        }

        //抬起键
        @Override
        public void keyReleased(KeyEvent e) {

//            System.out.println("你松开了键盘"+e.getKeyCode());
            plank.minusDirection(e);
        }
    }


    private Image offScreenImage = null;

    //下面是不会频闪
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(FRAME_WINDTH, FRAME_HIGHT); //游戏窗口
        }
        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);

    }

    //Runnable 重写方法
    public void run() {
        while (true) {
            if (enemyPlane.isLive()) {
                enemyPlane.move();
            }

            repaint();

            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}