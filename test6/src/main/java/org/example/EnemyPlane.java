package org.example;

import java.awt.*;

//public class EnemyPlane extends GameObject implements Runnable {
//
//    private boolean live;   // 敌机存活状态
//
//    public EnemyPlane(Image img, int x, int y, int speed) {
//        super(img, x, y, speed);
//        live = true;
//    }
//
//    @Override
//    public void drawMySelf(Graphics g) {
//        if (!live) {
//            return;
//        }
//
//        super.drawMySelf(g);
//    }
//
//    public boolean isLive() {
//        return live;
//    }
//
//    public void setLive(boolean live) {
//        this.live = live;
//    }
//
//    @Override
//    public void run() {
//        while (live) {
//            move(); // 移动敌机
//            try {
//                Thread.sleep(20); // 控制敌机移动的速度
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    // 敌机移动的方法
//    public void move() {
//        // 根据速度移动敌机的位置
//        y += speed;
//
//        // 判断敌机是否超出边界，超出边界后将敌机标记为已消失
//        if (y > GameUtil.FRAME_HIGHT) {
//            live = false;
//        }
//    }
//
//    public Rectangle getRec() {
//        return new Rectangle(x, y, img.getWidth(null), img.getHeight(null));
//    }
//}

public class EnemyPlane extends GameObject implements Runnable {

    private boolean live;   // 敌机存活状态
    private int direction;  // 飞行方向，1表示向下飞行，-1表示向上飞行

    public EnemyPlane(Image img, int x, int y, int speed) {
        super(img, x, y, speed);
        live = true;
        direction = 1; // 默认向下飞行
    }

    @Override
    public void drawMySelf(Graphics g) {
        if (!live) {
            return;
        }

        super.drawMySelf(g);
        // 根据速度和飞行方向移动敌机的位置
        y += speed * direction;

        // 判断敌机是否超出边界，超出边界后改变飞行方向
        if (y > GameUtil.FRAME_HIGHT || y < 0) {
            direction = -direction;
        }
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    @Override
    public void run() {
        while (live) {
            try {
                Thread.sleep(100); // 控制敌机移动的速度，增加等待时间以减慢速度
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 敌机移动的方法
    public void move() {
        // 根据速度和飞行方向移动敌机的位置
        y += speed * direction;

        // 判断敌机是否超出边界，超出边界后改变飞行方向
        if (y > GameUtil.FRAME_HIGHT || y < 0) {
            direction = -direction;
        }
    }
}