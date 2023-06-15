package org.example;

import java.awt.*;

/*
炮弹类
*/

public class Shell extends GameObject {

    double degree;   //角度

    @Override
    public void drawMySelf(Graphics g) {
        //炮弹基础设计
        Color c = g.getColor();
        g.setColor(Color.red);
        g.fillOval(x, y, width, height);

        //炮弹沿着任意角度飞行
        x += speed * Math.cos(degree);
        y += speed * Math.sin(degree);

        //实现边界碰撞回弹
        if (y > GameUtil.FRAME_HIGHT || y < 10) {  //>35边界回吞小球
            degree = -degree;
        }
        if (x > (GameUtil.FRAME_WINDTH - 32) || x < 0) {
            degree = Math.PI - degree;
        }


        g.setColor(c);
    }

    public Shell() {

        degree = Math.random() * Math.PI * 2;  // 随机角度  确保角度在0 到 2π 之间。
//        degree = Math.random() * Math.PI * 15;  //随机角度  !!!!
        //炮弹默认出现的位置
        x = 120;
        y = 120;
        width = 10;
        height = 10;
        speed = 4;
    }


}
