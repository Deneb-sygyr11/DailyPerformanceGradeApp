package DataStatistics;

import UserManagement.UserManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class DataStatistics {
    /**
     * 统计模块：分为周统计与月统计<p>
     * 周统计：{@code getClassTimeWeekly},{@code getQuestionNumWeekly},
     * {@code getEnglishWordNumWeekly},{@code getRunningTimeWeekly}<p>
     * 月统计：{@code getClassTimeMonthly},{@code getQuestionNumMonthly},
     * {@code getEnglishWordNumMonthly},{@code getRunningTimeMonthly}
     */

    //目前仅支持统计分数(具体数据统计后续会支持)

    //同一周的情况相对复杂，这里将根据一周任意一天求出该周第一天和最后一天的方法单独列出来
    private static String getDateOfMon(int year, int month, int day, int weekDayNum) {
        if (weekDayNum > day) {
            int gap = weekDayNum - day;
            switch (month) {
                case 2, 4, 6, 8, 9, 11:
                    month--;
                    day = 32 - gap;
                    break;
                case 5, 7, 10, 12:
                    month--;
                    day = 31 - gap;
                    break;
                case 1:
                    year--;
                    month = 12;
                    day = 32 - gap;
                    break;
                case 3:
                    month--;
                    if (year % 4 == 0) {
                        day = 30 - gap;
                    } else {
                        day = 29 - gap;
                    }
                    break;
            }
        } else {
            day = day - weekDayNum + 1;
        }
        return year + " " + month + " " + day;
    }

    private static String getDateOfSun(int year, int month, int day, int weekDayNum) {
        switch (month) {
            case 1, 3, 5, 7, 8, 10:
                day = (day + 7 - weekDayNum) % 31;
                month += (day + 6 - weekDayNum) / 31;
                break;
            case 4, 6, 9, 11:
                day = (day + 7 - weekDayNum) % 30;
                month += (day + 6 - weekDayNum) / 30;
                break;
            case 2:
                if (year % 4 == 0) {
                    day = (day + 7 - weekDayNum) % 29;
                    month += (day + 6 - weekDayNum) / 29;
                } else {
                    day = (day + 7 - weekDayNum) % 28;
                    month += (day + 6 - weekDayNum) / 28;
                }
                break;
            case 12:
                day = (day + 7 - weekDayNum) % 31;
                month += (day + 6 - weekDayNum) / 31;
                year += (day + 6 - weekDayNum) / 31;
                break;
        }
        return year + " " + month + " " + day;
    }

    public static String getClassTimeWeekly(String userName, int year, int month, int day, int weekDayNum) {
        if (!UserManager.isContained(userName)) {
            System.out.println("未知用户！");
            return null;
        }
        String[] MonDate = getDateOfMon(year, month, day, weekDayNum).trim().split("\\s+");
        String[] SunDate = getDateOfSun(year, month, day, weekDayNum).trim().split("\\s+");
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\" + userName + "\\classTimeGrade.txt"))) {
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                String[] s = line.trim().split("\\s+");
                if (((Integer.parseInt(s[0]) == Integer.parseInt(MonDate[0])
                        && (Integer.parseInt(s[1]) == Integer.parseInt(MonDate[1]) && Integer.parseInt(s[2]) >= Integer.parseInt(MonDate[2]))
                        || (Integer.parseInt(s[1]) > Integer.parseInt(MonDate[1]))) || Integer.parseInt(s[0]) > Integer.parseInt(MonDate[0]))
                        && ((Integer.parseInt(s[0]) <= Integer.parseInt(SunDate[0])
                        && (Integer.parseInt(s[1]) == Integer.parseInt(SunDate[1]) && Integer.parseInt(s[2]) <= Integer.parseInt(SunDate[2]))
                        || (Integer.parseInt(s[1]) < Integer.parseInt(SunDate[1]))) || Integer.parseInt(s[0]) < Integer.parseInt(SunDate[0]))) {//好大一坨判断条件。。。
                    sb.append(LocalDate.of(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])).getDayOfWeek().getValue()).
                            append(" ").append(s[6]).append(" ");
                } else if (Integer.parseInt(s[1]) > Integer.parseInt(SunDate[1])) {
                    break;
                }
            }
            sb.append("\n");
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getQuestionWeekly(String userName, int year, int month, int day, int weekDayNum) {
        if (!UserManager.isContained(userName)) {
            System.out.println("未知用户！");
            return null;
        }
        String[] MonDate = getDateOfMon(year, month, day, weekDayNum).trim().split("\\s+");
        String[] SunDate = getDateOfSun(year, month, day, weekDayNum).trim().split("\\s+");
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\" + userName + "\\workGrade.txt"))) {
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                String[] s = line.trim().split("\\s+");
                if (((Integer.parseInt(s[0]) == Integer.parseInt(MonDate[0])
                        && (Integer.parseInt(s[1]) == Integer.parseInt(MonDate[1]) && Integer.parseInt(s[2]) >= Integer.parseInt(MonDate[2]))
                        || (Integer.parseInt(s[1]) > Integer.parseInt(MonDate[1]))) || Integer.parseInt(s[0]) > Integer.parseInt(MonDate[0]))
                        && ((Integer.parseInt(s[0]) <= Integer.parseInt(SunDate[0])
                        && (Integer.parseInt(s[1]) == Integer.parseInt(SunDate[1]) && Integer.parseInt(s[2]) <= Integer.parseInt(SunDate[2]))
                        || (Integer.parseInt(s[1]) < Integer.parseInt(SunDate[1]))) || Integer.parseInt(s[0]) < Integer.parseInt(SunDate[0]))) {//好大一坨判断条件。。。
                    sb.append(LocalDate.of(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])).getDayOfWeek().getValue()).
                            append(" ").append(s[5]).append(" ");
                } else if (Integer.parseInt(s[1]) > Integer.parseInt(SunDate[1])) {
                    break;
                }
            }
            sb.append("\n");
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEnglishWordNumWeekly(String userName, int year, int month, int day, int weekDayNum) {
        if (!UserManager.isContained(userName)) {
            System.out.println("未知用户！");
            return null;
        }
        String[] MonDate = getDateOfMon(year, month, day, weekDayNum).trim().split("\\s+");
        String[] SunDate = getDateOfSun(year, month, day, weekDayNum).trim().split("\\s+");
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\" + userName + "\\englishWordGrade.txt"))) {
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                String[] s = line.trim().split("\\s+");
                if (((Integer.parseInt(s[0]) == Integer.parseInt(MonDate[0])
                        && (Integer.parseInt(s[1]) == Integer.parseInt(MonDate[1]) && Integer.parseInt(s[2]) >= Integer.parseInt(MonDate[2]))
                        || (Integer.parseInt(s[1]) > Integer.parseInt(MonDate[1]))) || Integer.parseInt(s[0]) > Integer.parseInt(MonDate[0]))
                        && ((Integer.parseInt(s[0]) <= Integer.parseInt(SunDate[0])
                        && (Integer.parseInt(s[1]) == Integer.parseInt(SunDate[1]) && Integer.parseInt(s[2]) <= Integer.parseInt(SunDate[2]))
                        || (Integer.parseInt(s[1]) < Integer.parseInt(SunDate[1]))) || Integer.parseInt(s[0]) < Integer.parseInt(SunDate[0]))) {//好大一坨判断条件。。。
                    sb.append(LocalDate.of(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])).getDayOfWeek().getValue()).
                            append(" ").append(s[5]).append(" ");
                } else if (Integer.parseInt(s[1]) > Integer.parseInt(SunDate[1])) {
                    break;
                }
            }
            sb.append("\n");
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getRunningTimeWeekly(String userName, int year, int month, int day, int weekDayNum) {
        if (!UserManager.isContained(userName)) {
            System.out.println("未知用户！");
            return null;
        }
        String[] MonDate = getDateOfMon(year, month, day, weekDayNum).trim().split("\\s+");
        String[] SunDate = getDateOfSun(year, month, day, weekDayNum).trim().split("\\s+");
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\" + userName + "\\runningGrade.txt"))) {
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                String[] s = line.trim().split("\\s+");
                if (((Integer.parseInt(s[0]) == Integer.parseInt(MonDate[0])
                        && (Integer.parseInt(s[1]) == Integer.parseInt(MonDate[1]) && Integer.parseInt(s[2]) >= Integer.parseInt(MonDate[2]))
                        || (Integer.parseInt(s[1]) > Integer.parseInt(MonDate[1]))) || Integer.parseInt(s[0]) > Integer.parseInt(MonDate[0]))
                        && ((Integer.parseInt(s[0]) <= Integer.parseInt(SunDate[0])
                        && (Integer.parseInt(s[1]) == Integer.parseInt(SunDate[1]) && Integer.parseInt(s[2]) <= Integer.parseInt(SunDate[2]))
                        || (Integer.parseInt(s[1]) < Integer.parseInt(SunDate[1]))) || Integer.parseInt(s[0]) < Integer.parseInt(SunDate[0]))) {//好大一坨判断条件。。。
                    sb.append(LocalDate.of(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])).getDayOfWeek().getValue()).
                            append(" ").append(s[6]).append(" ");
                } else if (Integer.parseInt(s[1]) > Integer.parseInt(SunDate[1])) {
                    break;
                }
            }
            sb.append("\n");
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getClassTimeMonthly(String userName, int year, int month) {
        if (!UserManager.isContained(userName)) {
            System.out.println("未知用户！");
            return null;
        }
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\" + userName + "\\classTimeGrade.txt"))) {
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.trim().split("\\s+");
                if (Integer.parseInt(s[0]) == year && Integer.parseInt(s[1]) == month) {
                    sb.append(s[2]).append(" ").append(s[6]).append(" ");
                } else if (!(Integer.parseInt(s[0]) <= year && Integer.parseInt(s[1]) <= month)) {
                    break;
                }
            }
            sb.append("\n");
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getQuestionMonthly(String userName, int year, int month) {
        if (!UserManager.isContained(userName)) {
            System.out.println("未知用户！");
            return null;
        }
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\" + userName + "\\workGrade.txt"))) {
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.trim().split("\\s+");
                if (Integer.parseInt(s[0]) == year && Integer.parseInt(s[1]) == month) {
                    sb.append(s[2]).append(" ").append(s[6]).append(" ");
                } else if (!(Integer.parseInt(s[0]) <= year && Integer.parseInt(s[1]) <= month)) {
                    break;
                }
            }
            sb.append("\n");
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEnglishWordNumMonthly(String userName, int year, int month) {
        if (!UserManager.isContained(userName)) {
            System.out.println("未知用户！");
            return null;
        }
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\" + userName + "\\englishWordGrade.txt"))) {
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.trim().split("\\s+");
                if (Integer.parseInt(s[0]) == year && Integer.parseInt(s[1]) == month) {
                    sb.append(s[2]).append(" ").append(s[6]).append(" ");
                } else if (!(Integer.parseInt(s[0]) <= year && Integer.parseInt(s[1]) <= month)) {
                    break;
                }
            }
            sb.append("\n");
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getRunningTimeMonthly(String userName, int year, int month) {
        if (!UserManager.isContained(userName)) {
            System.out.println("未知用户！");
            return null;
        }
        try (BufferedReader br = new BufferedReader(
                new FileReader("src\\Users\\userData\\" + userName + "\\runningGrade.txt"))) {
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.trim().split("\\s+");
                if (Integer.parseInt(s[0]) == year && Integer.parseInt(s[1]) == month) {
                    sb.append(s[2]).append(" ").append(s[6]).append(" ");
                } else if (!(Integer.parseInt(s[0]) <= year && Integer.parseInt(s[1]) <= month)) {
                    break;
                }
            }
            sb.append("\n");
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
