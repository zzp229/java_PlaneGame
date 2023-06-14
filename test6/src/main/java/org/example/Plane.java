package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;
/*
//飞机类
*/

public class Plane extends GameObject {

    boolean left, up, right, down;
    boolean live = true; //定义飞机存活

    public Plane(Image img, int x, int y, int speed) {
        super(img, x, y, speed);
        live = true;
    }


    //让飞机动起来
    @Override
    public void drawMySelf(Graphics g) {

        if (!live) {
            return;
        }

        super.drawMySelf(g);
///根据方向，计算飞机的新坐标

        if (left) {
            x -= speed;
        }
        if (right) {
            x += speed;
        }
        if (up) {
            y -= speed;
        }
        if (down) {
            y += speed;
        }


        //下面判断是为了实现飞机到达边框时不会出界，
        // 实现原理是判断坐标到达边框时减去所给方向的位移，一加一减之后就是停留在原地了
        if (x < 0) { //飞机到达左边框出不去
            x += speed;
        }
        if (y < 13) { //飞机到达上边框出不去
            y += speed;
        }

        // 边界检测
        // if (x < 0) {
        // x = 0; // 将飞机的横坐标设置为 0，防止越过左边界
        // }
        //右边框出不去!!!!!
        if (x > (GameUtil.FRAME_WINDTH - 157)) { //155

            x = GameUtil.FRAME_WINDTH + speed - 170; // 将飞机的横坐标设置为游戏窗口的宽度减去飞机宽度，防止越过右边界
        }
        // if (y < 0) {
        // y = 0; // 将飞机的纵坐标设置为 0，防止越过上边界
        // }

        ///下边框不越界
        if (y > (GameUtil.FRAME_HIGHT - height)) {
            y = GameUtil.FRAME_WINDTH - speed + 50; // 将飞机的纵坐标设置为游戏窗口的高度减去飞机高度，防止越过下边界
        }


    }

    //以下为控制方向的两个变量，按下某个键，增加相应方向
    public void addDirection(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: //按下左键
                left = true;
                break;
            case KeyEvent.VK_RIGHT: //按下右键
                right = true;
                break;
            case KeyEvent.VK_UP: //按下上键
                up = true;
                break;
            case KeyEvent.VK_DOWN: //按下下键
                down = true;
                break;

        }

    }

    ///抬起按键
    public void minusDirection(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: //抬起左键
                left = false;
                break;
            case KeyEvent.VK_RIGHT: //抬起右键
                right = false;
                break;
            case KeyEvent.VK_UP: //抬起上键
                up = false;
                break;
            case KeyEvent.VK_DOWN: //抬起下键
                down = false;
                break;

        }

    }

    ///添加敌机后：
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

}