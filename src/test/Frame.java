package test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {

    public Frame() {
        this.add(new Panel());
        this.setTitle("Example");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(500,500);
        this.setResizable(true);
    }

    public static void main(String[] args) {
        new Frame();
        // Программа будет работать, пока таймер не остановится
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}