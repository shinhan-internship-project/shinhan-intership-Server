package shinhanIntern.shinhan.utils;

public class Sort {
    public static int mapGrade(String grade) {
        int score = (grade.charAt(0) - 'A') * 3;
        score += mapSign(grade);

        System.out.println("grade: " + grade + ", score: " + score);
        return score;
    }

    public static int mapSign(String grade) {
        if (grade.length() == 1) {
            return 0;
        }

        if (grade.charAt(1) == '+') {
            return -1;
        }
        return 1;
    }
}
