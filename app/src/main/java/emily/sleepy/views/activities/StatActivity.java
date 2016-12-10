package emily.sleepy.views.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import emily.sleepy.R;
import emily.sleepy.constants.Constants;
import emily.sleepy.services.LightSensorService;
import emily.sleepy.services.ServiceManager;

public class StatActivity extends AppCompatActivity {
    private static final String TAG = StatActivity.class.getName();
    TextView phoneUsageTimer;
    Switch switchLightSensor;
    ServiceManager mServiceManager;
    private long AppOpenTime = 0L;
    private long StartTime = System.currentTimeMillis();
    PowerManager pm;

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
        }else{
            // stop timer
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        phoneUsageTimer =(TextView) findViewById(R.id.textViewPhoneUsageTimer);
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                String text ="";
                long currentTime = System.currentTimeMillis();
                handler.postDelayed(this, 1000);
                long timeDifference = (currentTime - StartTime);
                String hours = Long.toString((timeDifference/(1000*60*60))%24);
                String minutes = Long.toString((timeDifference/(1000*60))%60);
                String sec = Long.toString((timeDifference/(1000))%60);
//                SimpleDateFormat eTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
//                String timeElapsed = Long.toString();
//                String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                phoneUsageTimer.setText(hours + ":" + minutes + ":" + sec);
            }
        };
        handler.postDelayed(r, 0000);

//        phoneUsageTimer.setText(String.format(Locale.getDefault(),"Hi"));

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
                    mServiceManager.startSensorService(LightSensorService.class);
                }else{
                    Log.d(TAG, "switch light sensor off");
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

}
