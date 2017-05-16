package club.ah12530.myim.uitls;


import android.text.TextUtils;

public class StringUtils {

    /**获取英文首字母*/
    public static String getInitial(String contact){
        if (TextUtils.isEmpty(contact)){
            return contact;
        }else {
            return contact.substring(0,1).toUpperCase();
        }
    }
}
