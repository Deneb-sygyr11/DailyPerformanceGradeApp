package UserManagement;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Comparator;

public class UserManager {
    /**
     * 该类用于管理用户及用户数据，分为四个模块：私有化方法模块，初始化模块，用户操作管理模块，用户数据管理模块
     * 该类中所有路径中src\\在打包前必须去除（
     */

    private static final String userNames = "src\\Users\\userNames.txt";

    //判断用户名是否已经存在
    public static boolean isContained(String userName){
        try (BufferedReader br = new BufferedReader(new FileReader(userNames))){
            String line;
            while ((line = br.readLine()) != null){
                if (userName.equals(line)) {
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * 私有化方法(便于内部操作，防止外界非法调用)
     */
    //密码校验(输入为已加密密码)
    private static boolean comparePassword(String userName,String hashedPassword){
        if (!isContained(userName)) {
            System.out.println("未知用户！");
            return false;
        }
        try (BufferedReader br = new BufferedReader(new FileReader("src\\Users\\userData\\"+ userName+ "\\user.txt"))){
            br.readLine();
            return br.readLine().equals(hashedPassword);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    //加密模块：采用sha_256哈希加密算法加密
    private static String hashWithSHA256(String input){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            // 使用 Base64 编码输出（比十六进制更紧凑）
            return Base64.getEncoder().encodeToString(hashBytes);
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }


    /**
     * 初始化模块，确保文件已被创建和规范化。
     * <p>
     * 方法体均为私有，防止外部非法调用。
     * <p>
     * {@code initialUserFile}初始化用户文件，保留用户名与密码，其他数据归零。
     * <p>
     * {@code initialClassTimeGrade}初始化用户课程时间分数文件。
     * <p>
     * {@code initialWorkGrade}初始化用户做题分数文件。
     * <p>
     * {@code initialEnglishWordGrade}初始化英语单词分数文件。
     * <p>
     * {@code initialRunningGrade}初始化跑步分数文件。
     */
    //在确保已创建父目录的情况下使用
    public static void initialUserFile(String userName, String password) {
        String hashedPassword = hashWithSHA256(password);//加密密码
        String filePath = "src\\Users\\userData\\" + userName + "\\user.txt";
        try(PrintWriter writer = new PrintWriter(
                new FileWriter(filePath))){
            writer.print("");
        }catch (IOException e){
            throw new RuntimeException(e);
        }//先覆盖
        try (PrintWriter writer = new PrintWriter(
                new FileWriter(filePath,true))){
            writer.println(userName);
            writer.println(hashedPassword);
            writer.println(0 + " " + 0 + " " + 0);//totalClassNum + totalClassTime + classTimeGrade
            writer.println(0 + " " + 0);//totalQuestionNum + workGrade
            writer.println(0 + " " + 0);//totalEnglishNum + EnglishWordGrade
            writer.println(0 + " " + 0 + " " + 0);//totalEnglishNum + totalRunningTime + runningTimeGrade
        }catch (IOException ioException){
            throw new RuntimeException(ioException);
        }//再写入
    }
    //私有化(防止非法访问)
    private static void initialClassTimeGrade(String userName){
        try(PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\" + userName + "\\classTimeGrade.txt"))){
            writer.print("");//无文件时创建文件，文件存在时清空文件
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private static void initialWorkGrade(String userName){
        try(PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\" + userName + "\\workGrade.txt"))){
            writer.print("");
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private static void initialEnglishWordGrade(String userName){
        try(PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\" + userName + "\\englishWordGrade.txt"))){
            writer.print("");
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private static void initialRunningGrade(String userName){
        try(PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\" + userName + "\\runningGrade.txt"))){
            writer.print("");
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 用户操作管理模块
     * <p>
     * {@code createUser}注册用户<p>
     * {@code login}登录<p>
     * {@code deleteUser}删除用户
     */
    public static boolean createUser(String userName,String password) {
        if(isContained(userName)) {
            System.out.println("用户名已存在！");
            return false;
        }
        try {
            Path userPath = Paths.get("src","Users","userData",userName);
            Files.createDirectories(userPath);
            PrintWriter writer = new PrintWriter(new FileWriter(userNames,true));
            writer.println(userName);
            writer.close();
            initialUserFile(userName,password);//此处无需加密，初始化时会加密
            initialClassTimeGrade(userName);
            initialWorkGrade(userName);
            initialEnglishWordGrade(userName);
            initialRunningGrade(userName);
            System.out.println("创建成功");
            return true;
        } catch (IOException e) {
            System.out.println("写入过程发生错误");
            throw new RuntimeException(e);
        }
    }
    public static boolean login(String userName, String password){
        if(!isContained(userName)){
            System.out.println("未知用户！");
            return false;
        }
        return comparePassword(userName,hashWithSHA256(password));
    }
    public static boolean deleteUser(String userName, String password) {
        if (!isContained(userName)) {
            System.out.println("未知用户！");
            return false;
        } else if (!comparePassword(userName,hashWithSHA256(password))) {
            System.out.println("密码错误");
            return false;
        }
        Path path = Paths.get("src","Users","userData",userName);
        try {
            Files.walk(path)               // 深度遍历
                    .sorted(Comparator.reverseOrder()) // 文件先删，目录后删
                    .forEach(p -> {
                        try { Files.delete(p); }
                        catch (IOException e) { e.printStackTrace(); }
                    });
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        StringBuffer sb = new StringBuffer();
        try (BufferedReader br = new BufferedReader(new FileReader("src\\Users\\userNames.txt"))){
            String line;
            while ((line = br.readLine()) != null){
                if(!line.equals(userName)){
                    sb.append(line).append("\n");
                }
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter("src\\Users\\userNames.txt"))){
            writer.print(sb);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * 数据验证模块:<p>
     *     验证日期不可重复性...(还有功能在计划之中)<p>
     * 验证日期不可重复性：{@code dateExists}<p>
     */
    public static boolean dateExists(String userName,int year, int month, int day){
        if(!isContained(userName)){
            System.out.println("未知用户");
            return true;
        }
        try(BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\"+userName+"\\classTimeGrade.txt"))){//由于四个文件是同时操作的，只需要查看其中任意一个就好
            String line;
            while ((line = br.readLine())!=null){
                String[] s = line.trim().split("\\s+");
                if(year==Integer.parseInt(s[0])&&month==Integer.parseInt(s[1])&&day==Integer.parseInt(s[2])){
                    return true;
                }
            }
            return false;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 用户数据管理模块：核心数据管理模块、每日详细数据管理模块、重置模块<p>
     * 核心数据管理模块：{@code addClassTimeGrade},{@code addWorkGrade},{@code addEnglishGrade},{@code addRunningGrade}<p>
     * 每日详细数据管理模块：<p>
     * 1、添加模块：{@code recordClassTime},{@code recordQuestionNum},
     * {@code recordEnglishWordNum},{@code recordRunningTime}<p>
     * 2、覆盖模块(将最后一行数据覆盖，用于同日期输入情况)：{@code rewriteClassTime},{@code rewriteQuestionNum},
     * {@code rewriteEnglishWordNum},{@code rewriteRunningTime}<p>
     * 重置模块：{@code resetUserData}
     */
    //核心数据管理模块
    public static double addClassTimeGrade(String userName, int Time, int classNum){
        double count = PerformanceGrade.classTimeGrade(Time,classNum);
        if(!isContained(userName)){
            System.out.println("未知用户！");
            return -1;
        }
        StringBuffer sb = new StringBuffer();
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\"+userName+"\\user.txt"))){
            String line;
            int row = 0;
            while ((line = br.readLine()) != null){
                row++;
                if(row != 3) sb.append(line).append("\n");
                else {
                    String[] s = line.trim().split("\\s+");
                    count += Double.parseDouble(s[2]);
                    sb.append(classNum + Integer.parseInt(s[0])).append(' ').append(Time + Integer.parseInt(s[1]))
                            .append(' ').append(count).append("\n");
                }
            }
        }catch (IOException e){
            System.out.println("数据读取错误");
            throw new RuntimeException();
        }
        try (PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\"+userName+"\\user.txt"))){
            writer.print(sb);
            return count;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public static double addWorkGrade(String userName, int questionNum) {
        double count = PerformanceGrade.workGrade(questionNum);
        if (!isContained(userName)) {
            System.out.println("未知用户！");
            return -1;
        }
        StringBuffer sb = new StringBuffer();
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\"+userName+"\\user.txt"))){
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                row++;
                if (row != 4) sb.append(line).append("\n");
                else {
                    String[] s = line.trim().split("\\s+");
                    count += Double.parseDouble(s[1]);
                    sb.append(questionNum + Integer.parseInt(s[0])).append(' ').append(count).append("\n");
                }
            }
        } catch (IOException e) {
            System.out.println("数据读取错误");
            throw new RuntimeException();
        }
        try (PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\"+userName+"\\user.txt"))){
            writer.print(sb);
            return count;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public static double addEnglishGrade(String userName, int wordNum){
        double count = PerformanceGrade.englishWordGrade(wordNum);
        if(!isContained(userName)){
            System.out.println("未知用户！");
            return -1;
        }
        StringBuffer sb = new StringBuffer();
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\"+userName+"\\user.txt"))){
            String line;
            int row = 0;
            while ((line = br.readLine()) != null){
                row++;
                if(row != 5) sb.append(line).append("\n");
                else {
                    String[] s = line.trim().split("\\s+");
                    count += Double.parseDouble(s[1]);
                    sb.append(wordNum + Integer.parseInt(s[0])).append(' ').append(count).append("\n");
                }
            }
        }catch (IOException e){
            System.out.println("数据读取错误");
            throw new RuntimeException();
        }
        try (PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\"+userName+"\\user.txt"))){
            writer.print(sb);
            return count;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public static double addRunningGrade(String userName, int Time, int Num){
        double count = PerformanceGrade.runningTimeGrade(Time,Num);
        if(!isContained(userName)){
            System.out.println("未知用户！");
            return -1;
        }
        StringBuffer sb = new StringBuffer();
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\"+userName+"\\user.txt"))){
            String line;
            int row = 0;
            while ((line = br.readLine()) != null){
                row++;
                if(row != 6) sb.append(line).append("\n");
                else {
                    String[] s = line.trim().split("\\s+");
                    count += Double.parseDouble(s[2]);
                    sb.append(Num + Integer.parseInt(s[0])).append(' ').append(Time + Integer.parseInt(s[1]))
                            .append(' ').append(count).append("\n");
                }
            }
        }catch (IOException e){
            System.out.println("数据读取错误");
            throw new RuntimeException();
        }
        try (PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\"+userName+"\\user.txt"))){
            writer.print(sb);
            return count;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    //每日数据管理模块
    //记录模块
    public static boolean recordClassTime(String userName,int Time, int classNum){
        if(!isContained(userName)){
            System.out.println("未知用户！");
            return false;
        }
        LocalDate date = LocalDate.now();
        try (PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\"+userName+"\\classTimeGrade.txt",true))){
            writer.println(date.getYear() + " " + date.getMonthValue() + " " + date.getDayOfMonth() + " " + date.getDayOfWeek().getValue()
                    + "\t" + Time + ' ' + classNum + ' ' + PerformanceGrade.classTimeGrade(Time,classNum));
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean recordQuestionNum(String userName, int questionNum){
        if(!isContained(userName)){
            System.out.println("未知用户！");
            return false;
        }
        LocalDate date = LocalDate.now();
        try (PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\"+userName+"\\workGrade.txt",true))){
            writer.println(date.getYear() + " " + date.getMonthValue() + " " + date.getDayOfMonth() + " " + date.getDayOfWeek().getValue()
                    + "\t" + questionNum + ' ' + PerformanceGrade.workGrade(questionNum));
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean recordEnglishWordNum(String userName, int wordNum){
        if(!isContained(userName)){
            System.out.println("未知用户！");
            return false;
        }
        LocalDate date = LocalDate.now();
        try ( PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\"+userName+"\\englishWordGrade.txt",true))){
            writer.println(date.getYear() + " " + date.getMonthValue() + " " + date.getDayOfMonth() + " " + date.getDayOfWeek().getValue()
                    + "\t" + wordNum + ' ' + PerformanceGrade.englishWordGrade(wordNum));
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean recordRunningTime(String userName, int Time, int Num){
        if(!isContained(userName)){
            System.out.println("未知用户！");
            return false;
        }
        LocalDate date = LocalDate.now();
        try (PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\"+userName+"\\runningGrade.txt",true))){
            writer.println(date.getYear() + " " + date.getMonthValue() + " " + date.getDayOfMonth() + " " + date.getDayOfWeek().getValue()
                    + "\t" + Num + ' ' + Time + ' ' + PerformanceGrade.runningTimeGrade(Time,Num));
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //覆盖(写)模块
    public static boolean rewriteClassTime(String userName,int Time, int classNum){
        if(!isContained(userName)){
            System.out.println("未知用户！");
            return false;
        }
        StringBuffer sb = new StringBuffer();
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\"+userName+"\\classTimeGrade.txt"))){
            String line = br.readLine();
            String temp = line;
            while ((line = br.readLine()) != null){
                sb.append(temp);
                temp = line;
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        LocalDate date = LocalDate.now();
        try (PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\"+userName+"\\classTimeGrade.txt"))){
            writer.println(sb);
            writer.println(date.getYear() + " " + date.getMonthValue() + " " + date.getDayOfMonth() + " " + date.getDayOfWeek().getValue()
                    + "\t" + Time + ' ' + classNum + ' ' + PerformanceGrade.classTimeGrade(Time,classNum));
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean rewriteQuestionNum(String userName, int questionNum){
        if(!isContained(userName)){
            System.out.println("未知用户！");
            return false;
        }
        StringBuffer sb = new StringBuffer();
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\"+userName+"\\workGrade.txt"))){
            String line = br.readLine();
            String temp = line;
            while ((line = br.readLine()) != null){
                sb.append(temp);
                temp = line;
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        LocalDate date = LocalDate.now();
        try (PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\"+userName+"\\workGrade.txt"))){
            writer.println(sb);
            writer.println(date.getYear() + " " + date.getMonthValue() + " " + date.getDayOfMonth() + " " + date.getDayOfWeek().getValue()
                    + "\t" + questionNum + ' ' + PerformanceGrade.workGrade(questionNum));
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean rewriteEnglishWordNum(String userName, int wordNum){
        if(!isContained(userName)){
            System.out.println("未知用户！");
            return false;
        }
        StringBuffer sb = new StringBuffer();
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\"+userName+"\\englishWordGrade.txt"))){
            String line = br.readLine();
            String temp = line;
            while ((line = br.readLine()) != null){
                sb.append(temp);
                temp = line;
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        LocalDate date = LocalDate.now();
        try ( PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\"+userName+"\\englishWordGrade.txt"))){
            writer.println(sb);
            writer.println(date.getYear() + " " + date.getMonthValue() + " " + date.getDayOfMonth() + " " + date.getDayOfWeek().getValue()
                    + "\t" + wordNum + ' ' + PerformanceGrade.englishWordGrade(wordNum));
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean rewriteRunningTime(String userName, int Time, int Num){
        if(!isContained(userName)){
            System.out.println("未知用户！");
            return false;
        }
        StringBuffer sb = new StringBuffer();
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\"+userName+"\\runningGrade.txt"))){
            String line = br.readLine();
            String temp = line;
            while ((line = br.readLine()) != null){
                sb.append(temp);
                temp = line;
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        LocalDate date = LocalDate.now();
        try (PrintWriter writer = new PrintWriter(
                new FileWriter("src\\Users\\userData\\"+userName+"\\runningGrade.txt"))){
            writer.println(sb);
            writer.println(date.getYear() + " " + date.getMonthValue() + " " + date.getDayOfMonth() + " " + date.getDayOfWeek().getValue()
                    + "\t" + Num + ' ' + Time + ' ' + PerformanceGrade.runningTimeGrade(Time,Num));
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //重置模块
    public static boolean resetUserData(String userName, String password){
        String hashedPassword = hashWithSHA256(password);
        if (!isContained(userName)) {
            System.out.println("未知用户！");
            return false;
        } else if (!comparePassword(userName,hashedPassword)) {
            System.out.println("密码错误");
            return false;
        }
        initialUserFile(userName,hashedPassword);
        initialClassTimeGrade(userName);
        initialWorkGrade(userName);
        initialEnglishWordGrade(userName);
        initialRunningGrade(userName);
        return true;
    }
}