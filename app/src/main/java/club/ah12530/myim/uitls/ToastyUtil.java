package club.ah12530.myim.uitls;


import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ToastyUtil {
    public static void error(Context context,CharSequence message){
        Toasty.error(context.getApplicationContext(),message, Toast.LENGTH_SHORT,true).show();
    }
    public static void success(Context context,CharSequence message){
        Toasty.success(context.getApplicationContext(),message,Toast.LENGTH_SHORT,true).show();
    }
}
