package Frames;

import DataStatistics.DataStatistics;
import UserManagement.PerformanceGrade;
import UserManagement.UserManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainFrame {
    String userName;
    Interface anInterface;
    ArrayList<JLabel> gradeLabel;
    ArrayList<JLabel> weeklyGradeLabel;
    JTextField classTime, classNum, questionNum, englishWordNum, runningTime, runNum;
    JButton compute,save;

    public MainFrame(String userName){
        this.userName = userName;
        anInterface = new Interface(800,600,userName);
        gradeLabel = new ArrayList<>();
        weeklyGradeLabel = new ArrayList<>();
    }

    public void show(boolean b){
        addComponents();
        addListeners();
        addWeeklyOverview();
        anInterface.show(b);
    }

    private void addComponents() {
        LocalDate date = LocalDate.now();
        String year_month_day = String.format("%d - %d - %d",date.getYear(),date.getMonthValue(),date.getDayOfMonth());
        String s = new String();
        switch (date.getDayOfWeek().getValue()){
            case 1 -> s = "周一";
            case 2 -> s = "周二";
            case 3 -> s = "周三";
            case 4 -> s = "周四";
            case 5 -> s = "周五";
            case 6 -> s = "周六";
            case 7 -> s = "周日";
        }
        anInterface.addLabel(200,40,395,40,year_month_day,new Font("bold",Font.BOLD,30));
        anInterface.addLabel(80,40,615,40,s,new Font("bold",Font.BOLD,30));
        anInterface.addLabel(160, 40, 40, 20, "听课总时长(s):", new Font("bold", Font.BOLD, 20));
        anInterface.addLabel(100, 40, 80, 60, "课程总数:", new Font("bold", Font.BOLD, 20));
        anInterface.addLabel(100, 40, 80, 100, "刷题总数:", new Font("bold", Font.BOLD, 20));
        anInterface.addLabel(120, 40, 60, 140, "背单词数量:", new Font("bold", Font.BOLD, 20));
        anInterface.addLabel(140, 40, 60, 180, "跑步时长(s):", new Font("bold", Font.BOLD, 20));
        anInterface.addLabel(100, 40, 80, 220, "跑步圈数:", new Font("bold", Font.BOLD, 20));
        anInterface.addLabel(160,40,465,100,"今日分数",new Font("bold",Font.BOLD,30));
        anInterface.addLabel(100,40,440,150,"课程分数:",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(100,40,440,190,"做题分数:",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(100,40,440,230,"单词分数:",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(100,40,440,270,"跑步分数:",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(60,40,140,340,"周一",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(60,40,220,340,"周二",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(60,40,300,340,"周三",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(60,40,380,340,"周四",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(60,40,460,340,"周五",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(60,40,540,340,"周六",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(60,40,620,340,"周日",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(60,40,700,340,"总计",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(100,40,20,380,"课程分数",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(100,40,20,420,"做题分数",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(100,40,20,460,"单词分数",new Font("bold",Font.BOLD,20));
        anInterface.addLabel(100,40,20,500,"跑步分数",new Font("bold",Font.BOLD,20));
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
            for(JLabel jLabel : gradeLabel){
                jLabel.setVisible(false);
            }
            double classGrade = PerformanceGrade.classTimeGrade(Integer.parseInt(classTime.getText()),Integer.parseInt(classNum.getText()));
            double workGrade = PerformanceGrade.workGrade(Integer.parseInt(questionNum.getText()));
            double englishWordGrade = PerformanceGrade.englishWordGrade(Integer.parseInt(englishWordNum.getText()));
            double runningGrade = PerformanceGrade.runningTimeGrade(Integer.parseInt(runningTime.getText()), Integer.parseInt(runNum.getText()));
            gradeLabel.add(anInterface.addLabel(80,40,560,150,String.format("%.2f",classGrade),new Font("bold",Font.BOLD,20)));
            gradeLabel.add(anInterface.addLabel(80,40,560,190,String.format("%.2f",workGrade),new Font("bold",Font.BOLD,20)));
            gradeLabel.add(anInterface.addLabel(80,40,560,230,String.format("%.2f",englishWordGrade),new Font("bold",Font.BOLD,20)));
            gradeLabel.add(anInterface.addLabel(80,40,560,270,String.format("%.2f",runningGrade),new Font("bold",Font.BOLD,20)));
        });
        save.addActionListener(e -> {
            LocalDate date = LocalDate.now();
            int ct = Integer.parseInt(classTime.getText());
            int cn = Integer.parseInt(classNum.getText());
            int qn = Integer.parseInt(questionNum.getText());
            int ew = Integer.parseInt(englishWordNum.getText());
            int rt = Integer.parseInt(runningTime.getText());
            int rn = Integer.parseInt(runNum.getText());
            if(UserManager.dateExists(userName,date.getYear(),date.getMonthValue(),date.getDayOfMonth())){
                Interface question = new Interface(340,120,"dateExists!");
                question.addLabel(340,40,0,0,"当日成绩已记录，是否覆盖原数据？",
                        new Font("bold",Font.BOLD,20));
                JButton tbt = question.addButton(60,40,90,40,"是",
                        new Font("bold",Font.BOLD,20));
                JButton fbt = question.addButton(60,40,180,40,"否",
                        new Font("bold",Font.BOLD,20));
                tbt.addActionListener(e1 -> {
                    UserManager.rewriteClassTime(userName,ct,cn);
                    UserManager.rewriteQuestionNum(userName,qn);
                    UserManager.rewriteEnglishWordNum(userName,ew);
                    UserManager.rewriteRunningTime(userName,rt,rn);
                    Interface success = new Interface(200,80,"");
                    success.addLabel(100,40,50,0,"覆盖成功",new Font("bold",Font.BOLD,20));
                    success.show(true);new Timer(1000, e2 -> {
                        success.show(false);
                        ((Timer)e2.getSource()).stop(); // 停止定时器
                    }).start();
                    question.show(false);
                });
                fbt.addActionListener(e1 -> {
                    question.show(false);
                });
                question.show(true);
            }else {
                UserManager.addClassTimeGrade(userName,ct,cn);
                UserManager.addWorkGrade(userName,qn);
                UserManager.addEnglishGrade(userName,ew);
                UserManager.addRunningGrade(userName,rt,rn);
                UserManager.recordClassTime(userName,ct,cn);
                UserManager.recordQuestionNum(userName,qn);
                UserManager.recordEnglishWordNum(userName,ew);
                UserManager.recordRunningTime(userName,rt,rn);
            }
        });
        //todo:把UserManager里的用户数据管理模块实现

    }

    private void addWeeklyOverview(){
        for (JLabel jLabel : weeklyGradeLabel){
            jLabel.setVisible(false);
        }
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        int dow = date.getDayOfWeek().getValue();
        String ctw = DataStatistics.getClassTimeWeekly(userName,year,month,day,dow);
        String qw = DataStatistics.getQuestionWeekly(userName,year,month,day,dow);
        String eww = DataStatistics.getEnglishWordNumWeekly(userName,year,month,day,dow);
        String rw = DataStatistics.getRunningTimeWeekly(userName,year,month,day,dow);
        if(ctw!=null){
            String[] s1 = ctw.trim().split("\\s+");
            int[] arr = new int[7];
            double count = 0;
            for(int i = 0; i < s1.length; i+=2){
                int k = Integer.parseInt(s1[i])-1;
                arr[k]++;
                count += Double.parseDouble(s1[i+1]);
                weeklyGradeLabel.add(anInterface.addLabel(60,40,140+80*k,380,s1[i+1],
                        new Font("bold",Font.BOLD,20)));
            }
            for(int i = 0; i < 7; i++){
                if (arr[i] == 0){
                    weeklyGradeLabel.add(anInterface.addLabel(60,40,140+80*i,380,"  ---  ",
                            new Font("bold",Font.BOLD,20)));
                }
            }
            weeklyGradeLabel.add(anInterface.addLabel(60,40,700,380,String.format("%.2f",count),
                    new Font("bold",Font.BOLD,20)));
        }
        if(qw!=null){
            String[] s1 = qw.trim().split("\\s+");
            int[] arr = new int[7];
            double count = 0;
            for(int i = 0; i < s1.length; i+=2){
                int k = Integer.parseInt(s1[i])-1;
                arr[k]++;
                count += Double.parseDouble(s1[i+1]);
                weeklyGradeLabel.add(anInterface.addLabel(60,40,140+80*k,420,s1[i+1],
                        new Font("bold",Font.BOLD,20)));
            }
            for(int i = 0; i < 7; i++){
                if (arr[i] == 0){
                    weeklyGradeLabel.add(anInterface.addLabel(60,40,140+80*i,420,"  ---  ",
                            new Font("bold",Font.BOLD,20)));
                }
            }
            weeklyGradeLabel.add(anInterface.addLabel(60,40,700,420,String.format("%.2f",count),
                    new Font("bold",Font.BOLD,20)));
        }
        if(eww!=null){
            String[] s1 = eww.trim().split("\\s+");
            int[] arr = new int[7];
            double count = 0;
            for(int i = 0; i < s1.length; i+=2){
                int k = Integer.parseInt(s1[i])-1;
                arr[k]++;
                count += Double.parseDouble(s1[i+1]);
                weeklyGradeLabel.add(anInterface.addLabel(60,40,140+80*k,460,s1[i+1],
                        new Font("bold",Font.BOLD,20)));
            }
            for(int i = 0; i < 7; i++){
                if (arr[i] == 0){
                    weeklyGradeLabel.add(anInterface.addLabel(60,40,140+80*i,460,"  ---  ",
                            new Font("bold",Font.BOLD,20)));
                }
            }
            weeklyGradeLabel.add(anInterface.addLabel(60,40,700,460,String.format("%.2f",count),
                    new Font("bold",Font.BOLD,20)));
        }
        if(rw!=null){
            String[] s1 = rw.trim().split("\\s+");
            int[] arr = new int[7];
            double count = 0;
            for(int i = 0; i < s1.length; i+=2){
                int k = Integer.parseInt(s1[i])-1;
                arr[k]++;
                count += Double.parseDouble(s1[i+1]);
                weeklyGradeLabel.add(anInterface.addLabel(60,40,140+80*k,500,s1[i+1],
                        new Font("bold",Font.BOLD,20)));
            }
            for(int i = 0; i < 7; i++){
                if (arr[i] == 0){
                    weeklyGradeLabel.add(anInterface.addLabel(60,40,140+80*i,500,"  ---  ",
                            new Font("bold",Font.BOLD,20)));
                }
            }
            weeklyGradeLabel.add(anInterface.addLabel(60,40,700,500,String.format("%.2f",count),
                    new Font("bold",Font.BOLD,20)));
        }
    }

}
