package Frames;

import javax.swing.*;
import java.awt.*;

public class Interface{
    private JFrame jf;
    public Interface(int width, int height, String name){
        jf = new JFrame();
        jf.setSize(width, height);
        jf.setLayout(null);
        jf.setLocationRelativeTo(null);
        jf.setTitle(name);
        jf.setResizable(false);
        jf.setUndecorated(false);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public JButton addButton(int width, int height, int x, int y, String name, Font font){
        JButton jbt = new JButton(name);
        jbt.setSize(width,height);
        jbt.setFont(font);
        jf.add(jbt);
        jbt.setLocation(x,y);
        return jbt;
    }

    public JLabel addLabel(int width, int height, int x, int y, String name, Font font){
        JLabel jlb = new JLabel(name);
        jlb.setSize(width,height);
        jlb.setFont(font);
        jf.add(jlb);
        jlb.setLocation(x,y);
        return jlb;
    }

    public JTextField addTextField(int width, int height, int x, int y, Font font){
        JTextField jtf = new JTextField();
        jtf.setSize(width,height);
        jtf.setFont(font);
        jf.add(jtf);
        jtf.setLocation(x,y);
        return jtf;
    }

    public void show(boolean b){
        jf.setVisible(b);
    }
}
