package org.example;

import java.awt.*;

/*
///游戏里面所以物体都是矩形的
爆炸类  : 判断四个端点是否与另一个矩形区间相交，判断碰撞
*/


public class Explode {

    private double x, y;
    private static Image[] imgs = new Image[16];   //存储图片
    private int count;  ///统计次数
    private boolean live = true;   //飞机初始值
    //发生爆炸效果图
    static {
        for (int i = 0; i < 16; i++) {
            String imagePath = (i + 1) + ".gif";
            imgs[i] = GameUtil.getImage(imagePath);
        }
    }

    public Explode(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        if (!live) {
            return;
        }

        if (count < 16) {
            g.drawImage(imgs[count], (int) x, (int) y, null);
            count++;
        } else {
            live = false;
        }
    }

    //添加敌机后:
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}