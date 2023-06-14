package org.example;

import java.awt.*;
/*
//游戏物体的跟类
 */

public class GameObject {

    //对应图片
    Image img;
    //坐标
    int x, y;
    int speed; //速度
    int width, height; //物体的宽度和高度

    //画我自己
    public void drawMySelf(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    //画矩形,返回物体对应矩形，返回物体所在的矩形，便于后续的碰撞检测
    public Rectangle getRec() {
        return new Rectangle(x - 9, y - 9, width - 9, height - 9);
        ///返回的位置减去9是因为，飞机是矩形，导致发生碰撞比肉眼看到更快，所以减小四个端点使得飞机碰撞点更为集中在中心
    }

    //以下是重载构造器
    public GameObject() {
    }

    public GameObject(Image img, int x, int y, int speed, int width, int height) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }


    public GameObject(Image img, int x, int y, int speed) {
        this(img, x, y);
        this.speed = speed;

    }

    public GameObject(Image img, int x, int y) {

        this(img);
        this.x = x;
        this.y = y;
    }

    public GameObject(Image img) {
        this.img = img;
        if (this.img != null) {
            this.width = this.img.getWidth(null);
            this.height = this.img.getHeight(null);
        }
    }
}