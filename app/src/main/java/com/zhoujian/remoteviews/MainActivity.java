package com.zhoujian.remoteviews;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 RemoteViews是一个远程的View ，它可以在其他进程中显示，

 RemoteViews可以用于跨进程更新它的界面

 RemoteViews的应用主要在通知栏和桌面小部件的开发过程中

 通知栏是通过NotificationManager的notify()方法实现的

 桌面小部件是通过AppWidgetProvider来实现的

 */

public class MainActivity extends Activity
{

    private  NotificationManager notificationManager;
    private NotificationCompat.Builder mBuilder;
    @InjectView(R.id.bt_send)
    Button mBtSend;
    @InjectView(R.id.send)
    Button mSend;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        clickEvent();

    }

    private void clickEvent()
    {
        mBtSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sendDefaultNotification();
            }
        });

        mSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sendCustomNotification();
            }
        });


    }

    private void sendCustomNotification()
    {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        //设置图标
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setTicker("通知");
        //发送时间
        mBuilder.setWhen(System.currentTimeMillis());
        //设置默认的提示音，振动方式，灯光
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        //打开程序后图标消失
        mBuilder.setAutoCancel(true);
        Intent intent = new Intent(MainActivity.this, SeconActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        RemoteViews mRemoteViews = new RemoteViews(getPackageName(),R.layout.layout_notification);
        mRemoteViews.setTextViewText(R.id.tv_title,"自定义标题");
        mRemoteViews.setTextViewText(R.id.tv_content,"自定义内容");
        mRemoteViews.setImageViewResource(R.id.img,R.mipmap.ic_launcher);
        PendingIntent mPeendingIntent = PendingIntent.getActivity(MainActivity.this,0,new Intent(MainActivity.this,SeconActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.tv_content,mPeendingIntent);
        mBuilder.setContent(mRemoteViews);
        mBuilder.setContentIntent(pendingIntent);
        Notification notification1 = mBuilder.getNotification();
        //通过通知管理器发送通知
        notificationManager.notify(0, notification1);

    }

    private void sendDefaultNotification()
    {

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        //设置图标
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setTicker("通知");
        //设置标题
        mBuilder.setContentTitle("标题");
        //消息内容
        mBuilder.setContentText("点击查看详细内容");
        //发送时间
        mBuilder.setWhen(System.currentTimeMillis());
        //设置默认的提示音，振动方式，灯光
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        //打开程序后图标消失
        mBuilder.setAutoCancel(true);
        Intent intent = new Intent(MainActivity.this, SeconActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);
        Notification notification1 = mBuilder.getNotification();
        //通过通知管理器发送通知
        notificationManager.notify(0, notification1);

    }
}
