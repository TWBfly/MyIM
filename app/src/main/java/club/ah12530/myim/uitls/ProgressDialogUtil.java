package club.ah12530.myim.uitls;


import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtil {

    private static ProgressDialog progressDialog;

    public static void create(Context context) {

        progressDialog = new ProgressDialog(context);

        progressDialog.setCancelable(false);
    }

    public static void showDialog(String msg) {
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public static void dismissDialog() {
        progressDialog.dismiss();
    }
}
