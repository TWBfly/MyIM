package club.ah12530.myim.uitls;


import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadUitl {
    private static Executor executor = Executors.newSingleThreadExecutor();
    private static Handler handler = new Handler(Looper.getMainLooper());
    public static void runOnStubThread(Runnable runnable){
        executor.execute(runnable);
    }
    public static void runOnMainThread(Runnable runnable){
        handler.post(runnable);
    }
}
