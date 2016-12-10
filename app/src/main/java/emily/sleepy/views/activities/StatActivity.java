package emily.sleepy.views.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import emily.sleepy.R;
import emily.sleepy.constants.Constants;
import emily.sleepy.services.LightSensorService;
import emily.sleepy.services.ServiceManager;
import emily.sleepy.util.Session;

public class StatActivity extends AppCompatActivity {
    private static final String TAG = StatActivity.class.getName();
    TextView phoneUsageTimer;
    Switch switchLightSensor;
    ServiceManager mServiceManager;
    private long AppOpenTime = 0L;
    private long StartTime;
    PowerManager pm;
    List<Session> history = new ArrayList<Session>();

    private Handler mHandler;
    private Runnable r;
    private Boolean continueClock = false;

    // BroadcastReceiver for receiving intents
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {
                if (intent.getAction().equals(Constants.ACTION.BROADCAST_IS_SLEEP)) {
                    int isSleep = intent.getIntExtra(Constants.KEY.IS_SLEEP, -1);
                    StatActivity.this.processIsSleep(isSleep);
                }
            }
        }
    };

    private void processIsSleep(int isSleep){
        boolean isScreenOn = pm.isInteractive();
//        boolean isScreenOn = pm.isScreenOn();
        if(isSleep != 0 && isScreenOn){ // The user is using his/her phone before sleep
            // continue timer
            Log.d(TAG, "processIsSleep: continue timer");
            continueClock = true;
        }else{
            // stop timer
            continueClock = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        phoneUsageTimer =(TextView) findViewById(R.id.textViewPhoneUsageTimer);

        // Timer
        mHandler = new Handler();

        r = new Runnable() {

            public long tick(){
                return 1000L;
            }

            public void run() {
                String text ="";
//                long currentTime = System.currentTimeMillis();
                mHandler.postDelayed(this, 1000);
//                long timeDifference = (currentTime - StartTime);
                if(StatActivity.this.continueClock){
                    AppOpenTime += tick();
                }
                // Updating time in StatActivity
                String hours = Long.toString((AppOpenTime/(1000*60*60))%24);
                String minutes = Long.toString((AppOpenTime/(1000*60))%60);
                String sec = Long.toString((AppOpenTime/(1000))%60);
                phoneUsageTimer.setText(hours + ":" + minutes + ":" + sec);

                // Generating Notification every 5 seconds (for demo)
                int period = 5;
                if((AppOpenTime / 1000 ) % period == 0){
                    generateNotification(StatActivity.this, "Your phone needs to sleep too!");
                }
            }
        };
        mHandler.postDelayed(r, 0000);

        // CountDown Timer to notification


        this.mServiceManager = ServiceManager.getInstance(this);
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        //obtain references to the on/off light sensor switches and handle the toggling appropriately
        Log.d(TAG, "Before light sensor segment of code");
        switchLightSensor = (Switch) findViewById(R.id.switchLightSensor);
        switchLightSensor.setChecked(mServiceManager.isServiceRunning(LightSensorService.class));
        switchLightSensor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean enabled) {
                if (enabled){
                    Log.d(TAG, "switch light sensor on");
                    StartTime = System.currentTimeMillis();
                    mServiceManager.startSensorService(LightSensorService.class);
                }else{
                    Log.d(TAG, "switch light sensor off");
                    Session session = new Session(StartTime, System.currentTimeMillis(), AppOpenTime);
                    history.add(session);
                    continueClock = false;
                    AppOpenTime = 0L;
                    mServiceManager.stopSensorService(LightSensorService.class);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // Setting intent filter to filter intent from various services
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION.BROADCAST_IS_SLEEP);
        broadcastManager.registerReceiver(receiver, filter);
    }

    private void generateNotification(Context context, String message) {
        int icon = R.mipmap.ic_launcher;
        long when = System.currentTimeMillis();
        String appname = "USleep";
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        Notification notification;
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, USleep.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        notification = builder.setContentIntent(contentIntent)
                .setSmallIcon(icon)
                .setTicker(appname)
                .setWhen(when)
                .setAutoCancel(true)
                .setContentTitle(appname)
                .setContentText(message)
                .setSubText("Turn off your phone (╯°□°）╯︵ ┻━┻")
                .setColor(Color.BLUE)
                .build();

        notificationManager.notify((int) when, notification);
    }

}
