package food.delivery.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

    private static final Random random = new Random();

    public static Boolean isValidPassword(String password){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


    public static Boolean isValidUsername(String username) {
        Pattern pattern = Pattern.compile("^(?!\\d)[a-zA-Z\\d]*$");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }


    public static Boolean isValidPhoneNumber(String phoneNum){
        Pattern pattern = Pattern.compile("^([+]?\\d{3}[-\\s]?|)\\d{2}[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$");
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.matches();
    }


    public static Boolean isValidLength(String s, int min, int max) {
        return s.length() >= min && s.length() <= max;
    }


    public static Boolean isStringContainsLetter(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }


    public static String generateSalt(Integer size){

        StringBuilder code = new StringBuilder();
        for (int i=0; i<size; i++){
            int a = random.nextInt(2);
            code.append(a == 0 ? String.valueOf(randomLetter()) : String.valueOf(random.nextInt(10)));
        }

        return code.toString();
    }

    public static Character randomLetter(){
        int ch = random.nextInt(26) + 65;
        int lu = random.nextInt(2);
        ch = lu == 0 ? ch + 32 : ch;
        return (char) (ch);
    }

    public static Integer parseToInt(String s){
        try {
            return Integer.parseInt(s);
        }catch (Exception e){
            return -1;
        }
    }


    public static boolean checkNumber(String sub) {
        try {
            Integer code = Integer.parseInt(sub);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    public static List<Integer> parseString(String extra) {
        List<Integer> list = new ArrayList<>();
        if (extra == null) {
            return list;
        }
        String[] extraArray = extra.split(",");

        for (String e : extraArray) {
            list.add(Integer.parseInt(e));
        }

        return list;
    }


}
