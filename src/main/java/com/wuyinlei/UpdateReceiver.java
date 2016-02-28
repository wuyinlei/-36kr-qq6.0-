package com.wuyinlei;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.utils.adapter.data.SharedPreferencesHelper;
import com.wuyinlei.activity.R;
import com.wuyinlei.bean.UpdataInfoModel;
import com.wuyinlei.url.Config;

import java.io.File;
import java.util.HashMap;

/**
 * Created by 若兰 on 2016/2/27.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class UpdateReceiver extends BroadcastReceiver {

    private AlertDialog.Builder mDialog;
    public static final String UPDATE_ACTION = "wuyinlei";
    private SharedPreferencesHelper mSharedPreferencesHelper;
    private boolean isShowDialog;

    public UpdateReceiver() {
    }

    public UpdateReceiver(boolean isShowDialog) {
        super();
        this.isShowDialog = isShowDialog;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mSharedPreferencesHelper = SharedPreferencesHelper.getInstance(CNKApplication.getInstance());
        HashMap<String, Object> tempMap = CNKApplication.getInstance().getTempMap();

        UpdataInfoModel model = (UpdataInfoModel) tempMap.get(Config.KEY_APP_UPDATE);

        try {
            UpdateInformation.localVersion = CNKApplication.getInstance()
                    .getPackageManager()
                    .getPackageInfo(CNKApplication.getInstance()
                            .getPackageName(), 0)
                    .versionCode;
            UpdateInformation.versionName = CNKApplication.getInstance()
                    .getPackageManager()
                    .getPackageInfo(CNKApplication.getInstance()
                            .getPackageName(), 0).versionName;

        } catch (Exception e) {
            e.printStackTrace();
        }

        UpdateInformation.appname = CNKApplication.getInstance()
                .getResources().getString(R.string.app_name);

        UpdateInformation.serverVersion = Integer.parseInt(model.getServerVersion());

        UpdateInformation.serverflag = Integer.parseInt(model.getServerflag());
        UpdateInformation.lastForce = Integer.parseInt(model.getLastForce());
        UpdateInformation.updateUrl = model.getUpdateUrl();
        UpdateInformation.upgradeinfo = model.getUpdateDeinfo();

        checkVersion(context);

    }

    private void checkVersion(Context context) {
        if (UpdateInformation.localVersion < UpdateInformation.serverVersion){
            //需要进行更新
            mSharedPreferencesHelper.putIntValue(Config.IS_HAVE_NEW_VERSION,1);
            update(context);
        } else {
            mSharedPreferencesHelper.putIntValue(Config.IS_HAVE_NEW_VERSION,0);
            if (isShowDialog){
                noNewVersion(context);
            }
            clearUpdateFile(context);
        }
    }

    /**
     * 清楚升级文件
     * @param context
     */
    private void clearUpdateFile(Context context) {
        File updateDir;
        File updateFile;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            updateDir = new File(Environment.getExternalStorageDirectory(),UpdateInformation.downloadDir);

        } else {
            updateDir = context.getFilesDir();
        }

        updateFile = new File(updateDir.getPath(),"爱新闻1.1" + ".apk");
        if (updateFile.exists()) {
            Log.d("UpdateReceiver", "升级包存在，删除升级包");
            updateFile.delete();
        } else {
            Log.d("UpdateReceiver", "升级包不存在，不用删除升级包");
        }
    }

    /**
     * 没有新版本
     * @param context
     */
    private void noNewVersion(Context context) {

    }

    /**
     * 升级
     * @param context
     */
    private void update(Context context) {
        if (UpdateInformation.serverflag == 1){
            //官方推荐升级
            if (UpdateInformation.localVersion < UpdateInformation.lastForce){
                forceUpdate(context);
            } else {
                normalUpdate(context);
            }
        } else if (UpdateInformation.serverflag == 2){
            forceUpdate(context);
        }
    }

    //正常升级
    private void normalUpdate(final Context context) {
        mDialog = new AlertDialog.Builder(context);
        mDialog.setTitle("版本升级");
        mDialog.setMessage(UpdateInformation.upgradeinfo);
        mDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent mIntent = new Intent(context,UpdateService.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mIntent.putExtra("appname", UpdateInformation.appname);
                mIntent.putExtra("updateUrl",UpdateInformation.updateUrl);
                context.startService(mIntent);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    //强制升级
    private void forceUpdate(Context context) {

    }
}
