package test;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    Panel(){
        this.setPreferredSize(new Dimension(500,500)); //Задаётся размер окна через объект Dimension
        this.setBackground(Color.darkGray);
        this.setFocusable(true);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g); //Обновляется игровое поле
        draw(g);                 //Рисуется всё необходимое на очищенном полотне
    }
    public void draw(Graphics g){
        g.fillOval(-1,-1,50,50);
    }
}
