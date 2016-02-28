package com.wuyinlei;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.wuyinlei.activity.R;

import java.io.File;
import java.net.URI;

public class UpdateService extends Service {

    //BT字节参考量
    private static final float SIZE_BI = 1024L;
    //kb字节参考量
    private static final float SIZE_KB = SIZE_BI * 1024.0f;
    //MB字节参考量
    private static final float SIZE_MB = SIZE_KB * 1024.0f;

    private static final int DOWNLOAD_COMPLETE = 1;  //下载完成
    private static final int DOWNLOAD_NOMEMORY = -1;  //内存异常
    private static final int DOWNLOAD_FALE = -2; //下载失败


    private String appName = null;  //应用名字
    private String appUrl = null;  //应用升级地址
    private String updateDir = null;  //文件目录
    private File updateFile = null; //升级文件

    //通知栏
    private NotificationManager updateNotificationManager = null;
    private Notification updateNotification = null;

    private Intent updateIntent = null;  //下载完成

    private PendingIntent updatePendingIntent = null;  //在下载的时候

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        appName = intent.getStringExtra("appname");
        appUrl = intent.getStringExtra("updateUrl");

        updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        updateNotification = new Notification();

        updateNotification.icon = R.mipmap.ic_launcher;
        updateNotification.tickerText = "正在下载" + appName;

        updateNotification.when = System.currentTimeMillis();

        updateIntent = new Intent(this,CNKApplication.class);

        updatePendingIntent = PendingIntent.getActivity(this,0,updateIntent,0);

        updateNotification.contentIntent = updatePendingIntent;
        updateNotification.contentIntent.cancel();

        updateNotification.contentView = new RemoteViews(getPackageName(),R.layout.download_notification);

        updateNotification.contentView.setTextViewText(R.id.download_notice_name_tv,appName+ "正在下载");

        updateNotification.contentView.setTextViewText(R.id.download_notice_speed_tv,"0MB(0%)");

        updateNotificationManager.notify(0, updateNotification);

        new UpdateThread().execute();
    }

    class UpdateThread extends AsyncTask<Void,Void,Integer>{

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer == DOWNLOAD_COMPLETE) {
                Log.d("UpdateThread", "下载成功");
                String cmd = "chmod 777" + updateFile.getPath();
                try {
                    Runtime.getRuntime().exec(cmd);
                } catch (Exception e){
                    e.printStackTrace();
                }

                Uri uri = Uri.fromFile(updateFile);
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                installIntent.setDataAndType(uri, "application/vnd.android.packager-archive");
                updatePendingIntent = PendingIntent.getActivity(UpdateService.this,0,installIntent,0);
                updateNotification.contentIntent = updatePendingIntent;
                updateNotification.contentView.setTextViewText(R.id.download_notice_speed_tv,"下载完成，点击游览");
                updateNotification.tickerText = appName + "下载完成";
                updateNotification.when = System.currentTimeMillis();
                updateNotification.defaults = Notification.DEFAULT_SOUND;
                updateNotification.flags |= Notification.FLAG_AUTO_CANCEL;
                updateNotificationManager.notify(0,updateNotification);

                UpdateService.this.startActivity(installIntent);

                stopSelf();

            } else if (integer == DOWNLOAD_NOMEMORY) {
                Log.d("UpdateThread", "下载异常，内存问题");
                updateNotification.tickerText = appName + "下载失败";
                updateNotification.when = System.currentTimeMillis();
                updateNotification.contentView.setTextViewText(R.id.download_notice_speed_tv, "存储空间不足，请清理空间，重新下载");
                updateNotification.defaults = Notification.DEFAULT_SOUND;
                updateNotification.flags |= Notification.FLAG_AUTO_CANCEL;
                updateNotificationManager.notify(0, updateNotification);
                stopSelf();

            } else if (integer == DOWNLOAD_FALE) {
                Log.d("UpdateThread", "下载失败");
                updateNotification.tickerText = appName + "下载失败";
                updateNotification.when = System.currentTimeMillis();
                updateNotification.contentView.setTextViewText(R.id.download_notice_speed_tv,"下载过程中出错，请重新下载");
                updateNotification.defaults = Notification.DEFAULT_SOUND;
                updateNotification.flags |= Notification.FLAG_AUTO_CANCEL;
                updateNotificationManager.notify(0, updateNotification);
                stopSelf();
            }
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return downLoadUpdateFile(appUrl);
        }
    }

    private Integer downLoadUpdateFile(String appUrl) {

        int count = 0;
        long totalSize = 0;
        long downLpadSize = 0;
        URI encodeUri = null;
        //HttpGet httpGet = null;

        return null;
    }

    public UpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
