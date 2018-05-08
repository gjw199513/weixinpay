package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by gjw19 on 2018/5/2.
 */
public class PayUtil {

    public static String getCurrTime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyMMddHHmmss");
        String s = outFormat.format(now);
        return s;
    }

    public static String getRandomString(int lenght) {
        Random random = new Random();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < lenght; ++i) {
            int number = random.nextInt(3);
            long result = 0;
            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append(String.valueOf((char) result));
                    break;
                case 1:
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append(String.valueOf((char) result));
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }


        }
        return sb.toString();
    }


    public static String getTempStamp(){
        Date date = new Date();
        long a = date.getTime()/1000;
        String TempStamp = String.valueOf(a);
        return TempStamp;
    }

}
