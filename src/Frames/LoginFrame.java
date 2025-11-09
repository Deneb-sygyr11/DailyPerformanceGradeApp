package Frames;

import javax.swing.*;
import java.awt.*;
import UserManagement.UserManager;

public class LoginFrame{
    static Interface anInterface = new Interface(600,400,"登录/注册");
    static JTextField userName;
    static JTextField password;
    static JButton login;
    static JButton register;
    public static void show(boolean b){
        addComponents();
        addListeners();
        anInterface.show(b);
    }

    private static void addComponents(){
        anInterface.addLabel(40,20,20,20,"用户名:",new Font("plain",Font.PLAIN,12));
        anInterface.addLabel(40,20,20,60,"密码:",new Font("plain",Font.PLAIN,12));
        userName = anInterface.addTextField(120,20,70,20,new Font("plain",Font.PLAIN,12));
        password = anInterface.addTextField(120,20,70,60,new Font("plain",Font.PLAIN,12));
        login = anInterface.addButton(60,40,20,100,"登录",new Font("plain",Font.PLAIN,12));
        register = anInterface.addButton(60,40,100,100,"注册",new Font("plain",Font.PLAIN,12));
        //todo:添加用户删除按钮，添加用户名预览界面
    }

    private static void addListeners(){
        login.addActionListener(e -> {
            System.out.println(UserManager.login(userName.getText(), password.getText()));
            //todo:登陆成功则关闭当前页面，打开主界面，失败则打开对应原因界面
            if(UserManager.login(userName.getText(), password.getText())){
                MainFrame mainFrame = new MainFrame(userName.getText());
                show(false);
                mainFrame.show(true);
            }else {
                Interface failLogin = new Interface(300,80,"login failed!");
                failLogin.addLabel(200,40,60,0,"用户名或密码错误",new Font("bold",Font.BOLD,20));
                failLogin.show(true);
                new Timer(1000, e1 -> {
                    failLogin.show(false);
                    ((Timer)e1.getSource()).stop(); // 停止定时器
                }).start();
            }
        });
        register.addActionListener(e -> {
            //其实想做一个单独的注册界面，不过代码量估计有点大
            System.out.println(UserManager.createUser(userName.getText(), password.getText()));
            //todo:打开对应界面
        });
    }
}
