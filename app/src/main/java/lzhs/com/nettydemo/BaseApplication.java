package lzhs.com.nettydemo;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import lzhs.com.nettydemo.netty.NettyService;

/**
 * <br/>
 * 作者：LZHS<br/>
 * 时间： 2018/4/21 12:13<br/>
 * 邮箱：1050629507@qq.com
 */
public class BaseApplication extends Application {
    public static final String TAG = "BaseApplication";

    public static  BaseApplication mInstance=null;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        // 程序创建的时候执行
        Log.d(TAG, "onCreate");
        super.onCreate();
        mInstance=this;
        if (!isServiceRunning(this, NettyService.class.getName()))
            startService(getIntent());
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        Log.d(TAG, "onTerminate");
        super.onTerminate();
        stopService(getIntent());
    }

    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        Log.d(TAG, "onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        Log.d(TAG, "onTrimMemory");
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    private Intent getIntent() {
        return new Intent(this,NettyService.class);
    }


    /**
     * 判断服务是否在运行中
     *
     * @param context     即为Context对象
     * @param serviceName 即为Service的全名
     * @return 是否在运行中
     */
    private boolean isServiceRunning(Context context, String serviceName) {
        if (!TextUtils.isEmpty(serviceName) && context != null) {
            ActivityManager activityManager
                    = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ArrayList<RunningServiceInfo> runningServiceInfoList
                    = (ArrayList<RunningServiceInfo>) activityManager.getRunningServices(100);
            for (Iterator<RunningServiceInfo> iterator = runningServiceInfoList.iterator(); iterator.hasNext(); ) {
                RunningServiceInfo runningServiceInfo = (RunningServiceInfo) iterator.next();
                if (serviceName.equals(runningServiceInfo.service.getClassName().toString()))
                    return true;
            }
        }
        return false;
    }
}
