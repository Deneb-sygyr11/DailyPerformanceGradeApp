package UserManagement;

public class PerformanceGrade {

    public static double classTimeGrade(int totalTime, int classNum){
        //计算公式：（总时长-课程数*5）/ （100*课程数） 单位：秒
        //总时长为开始上课至最终下课时长，中途不暂停
        return Math.round((1.0*(totalTime-classNum*300)/(6000*classNum))*100)/100.0;
    }

    public static double workGrade(int questionNum){
        //计算公式：做题总数/10
        return Math.round((1.0*questionNum/10)*100)/100.0;
    }

    public static double englishWordGrade(int wordNum){
        //计算公式：单词总数/30
        return Math.round((1.0*wordNum/30)*100)/100.0;
    }

    public static double runningTimeGrade(int runningTime,int num){
        //计算公式：120*圈数/总时长 单位：秒
        return Math.round((1.0*120*num/runningTime)*100)/100.0;
    }
}
