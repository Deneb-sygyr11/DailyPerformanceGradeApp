import Frames.LoginFrame;
import Frames.MainFrame;
import UserManagement.UserManager;

public class Test {
    public static void main(String[] args) {
        //Interface test
        /*Interface test = new Interface(800,600,"test");
        test.addButton(80,60,0,0,"test",new Font("italic",Font.PLAIN,20));
        test.addLabel(400,60,20,100,"test label",new Font("italic",Font.PLAIN,20));*/

        //PerformanceGrade test
        /*System.out.println(PerformanceGrade.classTimeGrade(1234321, 3));
        System.out.println(PerformanceGrade.workGrade(34));
        System.out.println(PerformanceGrade.englishWordGrade(60));
        System.out.println(PerformanceGrade.runningTimeGrade(321,3));*/

        //UserManager test
        /*
        System.out.println(UserManager.createUser("sygyr11", "1234321"));
        System.out.println("---------------");
        System.out.println(UserManager.login("Alice","1234321"));
        System.out.println(UserManager.addClassTimeGrade("sygyr11",1234321,3));
        System.out.println(UserManager.addWorkGrade("sygyr11",34));
        System.out.println(UserManager.addEnglishGrade("sygyr11",60));
        System.out.println(UserManager.addRunningGrade("sygyr11",3,321));
        System.out.println(UserManager.recordClassTime("sygyr11",1234321,3));
        System.out.println(UserManager.recordQuestionNum("sygyr11",34));
        System.out.println(UserManager.recordQuestionNum("test",114514));
        System.out.println(UserManager.recordEnglishWordNum("sygyr11",60));
        System.out.println(UserManager.recordRunningTime("sygyr11",300,2));
        System.out.println(UserManager.resetUserData("sygyr11","123132"));
        System.out.println(UserManager.resetUserData("sygy11","1234321"));
        System.out.println(UserManager.deleteUser("sygyr11","12321"));
        System.out.println(UserManager.deleteUser("sygyr", "1234321"));
        System.out.println(UserManager.login("sygyr11", "1234321"));
        System.out.println(UserManager.deleteUser("sygyr11", "1234321"));*/
        /*System.out.println(UserManager.resetUserData("sygyr11","1234321"));*/
        /*System.out.println(UserManager.getClassTimeMonthly("test",2025,11));*/
        /*System.out.println(UserManager.getClassTimeWeekly("test",2025,11,11,2));*/

        //LoginFrame test
        /*LoginFrame.show(true);*/
        //MainFrame test
        MainFrame mainFrame = new MainFrame("test");
        mainFrame.show(true);
    }
}
