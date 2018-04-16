package kr.hs.dgsw.flow.helper;

import java.util.regex.Pattern;

public class Validation {
    private static String emailPattern = "^[a-zA-Z0-9]+@dgsw.hs.kr$";
    /**
     *
         ^                 # start-of-string
         (?=.*[0-9])       # a digit must occur at least once
         (?=.*[a-z])       # a lower case letter must occur at least once
         (?=.*[A-Z])       # an upper case letter must occur at least once
         (?=.*[@#$%^&+=])  # a special character must occur at least once
         (?=\S+$)          # no whitespace allowed in the entire string
         .{8,}             # anything, at least eight places though
         $                 # end-of-string
     *
     * reference url: https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
     */

    private static String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()])(?=\\S+$).{8,16}$";

    /**
     * @param email 검증할 이메일
     * @return 정규식과 맞으면 true, 정규식에 일치하지 않으면 false
     */

    public static boolean isValidEmail(String email) {
        return Pattern.matches(emailPattern, email);
    }

    /**
     * @param password 검증할 비밀번호 ( 소문자, 대문자, 특수문자 포함해서 8~16 )
     * @return 정규식과 맞으면 true, 정규식에 일치하지 않으면 false
     */

    public static boolean isValidPassword(String password) {
        return Pattern.matches(passwordPattern, password);
    }

    /**
     * @param password 사용자가 입력한 비밀번호
     * @param passwordRe 사용자가 입력한 비밀번호 재확인
     * @return password, passwordRe를 비교해서 일치하면 true, 일치하지 않으면 false
     */

    public static boolean isComparingPassword(String password, String passwordRe) {
        return password.equals(passwordRe);
    }

    /**
     *
     * @param value 공백을 제거한 후 문자열인지 확인
     * @return 아무것도 없다면 0을 리턴
     */
    public static boolean isBeEmptyValue(String value) {
        return value.isEmpty();
    }
}
