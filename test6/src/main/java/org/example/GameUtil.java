package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;


/*
工具类
 */
public class GameUtil {

    //背景宽度
    public static final int FRAME_WINDTH = 450;
    //背景高度
    public static final int FRAME_HIGHT = 540;

    //防止别人创建它,构造器私有
    private GameUtil() {

    }

    public static Image getImage(String path) {

        Image img = null;

        URL url = GameUtil.class.getClassLoader().getResource(path);

        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return img;
    }
}