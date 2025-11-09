package Frames;

import UserManagement.PerformanceGrade;
import UserManagement.UserManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame {
    String userName;
    Interface anInterface;
    ArrayList<JLabel> gradeLabel;
    JTextField classTime, classNum, questionNum, englishWordNum, runningTime, runNum;
    JButton compute,save;

    public MainFrame(String userName){
        this.userName = userName;
        anInterface = new Interface(800,600,userName);
        gradeLabel = new ArrayList<>();
    }

    public void show(boolean b){
        addComponents();
        addListeners();
        anInterface.show(b);
    }

    private void addComponents() {
        anInterface.addLabel(160, 40, 20, 20, "听课总时长(s):", new Font("bold", Font.BOLD, 20));
        anInterface.addLabel(100, 40, 60, 60, "课程总数:", new Font("bold", Font.BOLD, 20));
        anInterface.addLabel(100, 40, 60, 100, "刷题总数:", new Font("bold", Font.BOLD, 20));
        anInterface.addLabel(120, 40, 40, 140, "背单词数量:", new Font("bold", Font.BOLD, 20));
        anInterface.addLabel(140, 40, 40, 180, "跑步时长(s):", new Font("bold", Font.BOLD, 20));
        anInterface.addLabel(100, 40, 60, 220, "跑步圈数:", new Font("bold", Font.BOLD, 20));
        anInterface.addLabel(200,40,445,20,"今日分数",new Font("bold",Font.BOLD,30));
        anInterface.addLabel(100,40,420,70,"课程分数:",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(100,40,420,110,"做题分数:",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(100,40,420,150,"单词分数:",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(100,40,420,190,"跑步分数:",new Font("bold",Font.BOLD,20));
        classTime = anInterface.addTextField(100, 40, 190, 20, new Font("bold", Font.BOLD, 20));
        classNum = anInterface.addTextField(100, 40, 190, 60, new Font("bold", Font.BOLD, 20));
        questionNum = anInterface.addTextField(100, 40, 190, 100, new Font("bold", Font.BOLD, 20));
        englishWordNum = anInterface.addTextField(100, 40, 190, 140, new Font("bold", Font.BOLD, 20));
        runningTime = anInterface.addTextField(100, 40, 190, 180, new Font("bold", Font.BOLD, 20));
        runNum = anInterface.addTextField(100, 40, 190, 220, new Font("bold", Font.BOLD, 20));
        compute = anInterface.addButton(160, 40, 30, 280, "计算今日分数", new Font("bold", Font.BOLD, 20));
        save = anInterface.addButton(80,40,210,280,"保存",new Font("bold",Font.BOLD,20));
        //todo:添加清零按键，添加用户数据预览按键

    }

    private void addListeners(){
        compute.addActionListener(e -> {
            for(JLabel lable : gradeLabel){
                lable.setVisible(false);
            }
            double classGrade = PerformanceGrade.classTimeGrade(Integer.parseInt(classTime.getText()),Integer.parseInt(classNum.getText()));
            double workGrade = PerformanceGrade.workGrade(Integer.parseInt(questionNum.getText()));
            double englishWordGrade = PerformanceGrade.englishWordGrade(Integer.parseInt(englishWordNum.getText()));
            double runningGrade = PerformanceGrade.runningTimeGrade(Integer.parseInt(runningTime.getText()), Integer.parseInt(runNum.getText()));
            gradeLabel.add(anInterface.addLabel(80,40,540,70,String.format("%.2f",classGrade),new Font("bold",Font.BOLD,20)));
            gradeLabel.add(anInterface.addLabel(80,40,540,110,String.format("%.2f",workGrade),new Font("bold",Font.BOLD,20)));
            gradeLabel.add(anInterface.addLabel(80,40,540,150,String.format("%.2f",englishWordGrade),new Font("bold",Font.BOLD,20)));
            gradeLabel.add(anInterface.addLabel(80,40,540,190,String.format("%.2f",runningGrade),new Font("bold",Font.BOLD,20)));
        });
        save.addActionListener(e -> {
            int ct = Integer.parseInt(classTime.getText());
            int cn = Integer.parseInt(classNum.getText());
            int qn = Integer.parseInt(questionNum.getText());
            int ew = Integer.parseInt(englishWordNum.getText());
            int rt = Integer.parseInt(runningTime.getText());
            int rn = Integer.parseInt(runNum.getText());
            UserManager.addClassTimeGrade(userName,ct,cn);
            UserManager.addWorkGrade(userName,qn);
            UserManager.addEnglishGrade(userName,ew);
            UserManager.addRunningGrade(userName,rt,rn);
            UserManager.recordClassTime(userName,ct,cn);
            UserManager.recordQuestionNum(userName,qn);
            UserManager.recordEnglishWordNum(userName,ew);
            UserManager.recordRunningTime(userName,rt,rn);
        });
        //todo:把UserManager里的用户数据管理模块实现

    }
}
