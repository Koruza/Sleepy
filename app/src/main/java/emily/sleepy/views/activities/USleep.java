package emily.sleepy.views.activities;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ImageButton;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.app.NotificationManager;
import android.app.PendingIntent;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import emily.sleepy.R;

public class USleep extends AppCompatActivity {

    ImageButton imgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usleep);

        imgButton =(ImageButton)findViewById(R.id.startSleepButton);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(USleep.this, StatActivity.class));
            }
        });

        MyTimerTask myTask = new MyTimerTask();
        Timer myTimer = new Timer();

//        myTimer.schedule(myTask, 0, 900000);

        imgButton =(ImageButton)findViewById(R.id.startSleepButton);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(USleep.this, StatActivity.class));
            }
        });


    }

    class MyTimerTask extends TimerTask {
        public void run() {

            generateNotification(getBaseContext(), "Your phone needs to sleep too!");
        }
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
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_usleep);
//
//        imgButton =(ImageButton)findViewById(R.id.startSleepButton);
//        imgButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(USleep.this, StatActivity.class));
//            }
//        });
//
//    private void startAlarm() {
//            AlarmManager alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
//            long when = System.currentTimeMillis();         // notification time
//            Intent intent = new Intent(this, ReminderService.class);
//            PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
//            alarmManager.setRepeating(AlarmManager.RTC, when, (AlarmManager.INTERVAL_FIFTEEN_MINUTES / 3), pendingIntent);
//        }
//
//
//    public class ReminderService extends IntentService {
//        private static final int NOTIF_ID = 1;
//
//        public ReminderService(){
//            super("ReminderService");
//        }
//
//        @Override
//        protected void onHandleIntent(Intent intent) {
//            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            long when = System.currentTimeMillis();         // notification time
//            Notification notification = new Notification(R.drawable.icon, "reminder", when);
//            notification.defaults |= Notification.DEFAULT_SOUND;
//            notification.flags |= notification.FLAG_AUTO_CANCEL;
//            Intent notificationIntent = new Intent(this, StatActivity.class);
//            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent , 0);
//            notification.setLatestEventInfo(getApplicationContext(), "It's about time", "You should open the app now", contentIntent);
//            nm.notify(NOTIF_ID, notification);
//        }
//
//    }
//    }
//}



//public class USleep extends AppCompatActivity {
//
//    @SuppressWarnings("unused")
//    /** used for debugging purposes */
//    private static final String TAG = MainActivity.class.getName();
//
//    public enum PAGES {
//        MOTION_DATA(ExerciseFragment.class) {
//            @Override
//            public String getTitle() {
//                return "USleep";
//            }
//
//            @Override
//            public int getPageNumber() {
//                return 0;
//            }
//        },
//        ABOUT(AboutFragment.class) {
//            @Override
//            public String getTitle() {
//                return "StatActivity";
//            }
//
//            @Override
//            public int getPageNumber() {
//                return 2;
//            }
//        };
//
//
//        public String getTitle(){
//            return name();
//        }
//
//        /**
//         * Returns the fragment associated with the page
//        * @return Fragment object, null if an instantiate error occurs.
//                */
//        public Fragment getFragment(){
//            try {
//                return fragment.newInstance();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch(IllegalAccessException e) {
//                Log.d(TAG, "Cannot instantiate fragment. Constructor may be private.");
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        public int getPageNumber(){
//            return ordinal();
//        }
//
//        static int getCount(){
//            return values().length;
//        }
//
//        /**
//         * Constructor for a page. It requires a fragment class which defines the fragment
//         * that will be displayed in the tab.
//         * @param fragment class type that extends Fragment
//         */
//        PAGES(Class<? extends Fragment> fragment){
//            this.fragment = fragment;
//        }
//
//        /**
//         * The fragment class associated with the enum type.
//         */
//        private final Class<? extends Fragment> fragment;
//    }
//
//    /** Displays status messages, e.g. connection station. **/
//    private TextView txtStatus;
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
//        //the intent filter specifies the messages we are interested in receiving
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Constants.ACTION.BROADCAST_MESSAGE);
//        filter.addAction(Constants.ACTION.BROADCAST_STATUS);
//        broadcastManager.registerReceiver(receiver, filter);
//    }
//
//    @Override
//    protected void onStop() {
//        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
//        try {
//            broadcastManager.unregisterReceiver(receiver);
//        }catch (IllegalArgumentException e){
//            e.printStackTrace();
//        }
//        super.onStop();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_stat);
////        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
////        setSupportActionBar(myToolbar);
//
//        /* Maintains the tabs and the tab layout interactions. */
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
//            private final String[] tabTitles = new String[PAGES.getCount()];
//            private final Fragment[] fragments = new Fragment[PAGES.getCount()];
//            //instance initializer:
//            {
//                for (PAGES page : PAGES.values()) {
//                    tabTitles[page.getPageNumber()] = page.getTitle();
//                    fragments[page.getPageNumber()] = page.getFragment();
//                }
//            }
//
//            @Override
//            public android.app.Fragment getItem(int position) {
//                return fragments[position];
//            }
//
//            @Override
//            public CharSequence getPageTitle(int position) {
//                return tabTitles[position];
//            }
//
//            @Override
//            public int getCount() {
//                return PAGES.getCount();
//            }
//        });
////        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
////        assert tabLayout != null;
////        tabLayout.setupWithViewPager(viewPager);
////
////        txtStatus = (TextView) findViewById(R.id.status);
//
//        // if the activity was started by clicking a notification, then the intent contains the
//        // notification ID and can be used to set the proper tab.
//        if (getIntent() != null) {
//            int notificationID = getIntent().getIntExtra(Constants.KEY.NOTIFICATION_ID, Constants.NOTIFICATION_ID.ACCELEROMETER_SERVICE);
//            switch (notificationID){
//                case Constants.NOTIFICATION_ID.ACCELEROMETER_SERVICE:
//                    viewPager.setCurrentItem(PAGES.MOTION_DATA.getPageNumber());
//                    break;
//                case Constants.NOTIFICATION_ID.AUDIO_SERVICE:
//                    viewPager.setCurrentItem(PAGES.AUDIO_DATA.getPageNumber());
//                    break;
//                case Constants.NOTIFICATION_ID.LOCATION_SERVICE:
//                    viewPager.setCurrentItem(PAGES.LOCATION_DATA.getPageNumber());
//                    break;
//                case Constants.NOTIFICATION_ID.PPG_SERVICE:
//                    viewPager.setCurrentItem(PAGES.PPG_DATA.getPageNumber());
//                    break;
//            }
//        }
//
//        private void startAlarm() {
//            AlarmManager alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
//            long when = System.currentTimeMillis();         // notification time
//            Intent intent = new Intent(this, ReminderService.class);
//            PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
//            alarmManager.setRepeating(AlarmManager.RTC, when, (AlarmManager.INTERVAL_FIFTEEN_MINUTES / 3), pendingIntent);
//        }
//
//
//    public class ReminderService extends IntentService {
//        private static final int NOTIF_ID = 1;
//
//        public ReminderService(){
//            super("ReminderService");
//        }
//
//        @Override
//        protected void onHandleIntent(Intent intent) {
//            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            long when = System.currentTimeMillis();         // notification time
//            Notification notification = new Notification(R.drawable.icon, "reminder", when);
//            notification.defaults |= Notification.DEFAULT_SOUND;
//            notification.flags |= notification.FLAG_AUTO_CANCEL;
//            Intent notificationIntent = new Intent(this, StatActivity.class);
//            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent , 0);
//            notification.setLatestEventInfo(getApplicationContext(), "It's about time", "You should open the app now", contentIntent);
//            nm.notify(NOTIF_ID, notification);
//        }
//
//    }
//    }
//
//
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(final Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    /**
//     * Shows a removable status message at the bottom of the application.
//     * @param message the status message shown
//     */
//    public void showStatus(String message){
//        txtStatus.setText(message);
//    }
//
//    private final BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction() != null) {
//                if (intent.getAction().equals(Constants.ACTION.BROADCAST_MESSAGE)){
//                    int message = intent.getIntExtra(Constants.KEY.MESSAGE, -1);
//                    switch (message){
//                        case Constants.MESSAGE.ACCELEROMETER_SERVICE_STARTED:
//                            showStatus(getString(R.string.accelerometer_started));
//                            break;
//                        case Constants.MESSAGE.ACCELEROMETER_SERVICE_STOPPED:
//                            showStatus(getString(R.string.accelerometer_stopped));
//                            break;
//
//                    }
//                } else if (intent.getAction().equals(Constants.ACTION.BROADCAST_STATUS)){
//                    String message = intent.getStringExtra(Constants.KEY.STATUS);
//                    if (message != null) {
//                        showStatus(message);
//                    }
//                }
//            }
//        }
//    };
//}