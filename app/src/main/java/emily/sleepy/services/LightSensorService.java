package emily.sleepy.services;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import edu.umass.cs.MHLClient.client.MessageReceiver;
import edu.umass.cs.MHLClient.client.MobileIOClient;
import edu.umass.cs.MHLClient.sensors.SensorReading;
import emily.sleepy.R;
import emily.sleepy.constants.Constants;

/**
 * Created by kawo123 on 12/9/2016.
 * Modelled after cs390mb
 */
public class LightSensorService extends SensorService implements SensorEventListener {

    /** Used during debugging to identify logs by class */
    private static final String TAG = LightSensorService.class.getName();

    /** Sensor Manager object for registering and unregistering system sensors */
    private SensorManager mSensorManager;

    /** Manages the physical accelerometer sensor on the phone. */
    private Sensor mLightSensor;

    public LightSensorService(){
        // Do nothing
    }

    @Override
    protected void onServiceStarted() {
        Log.d(TAG, "light sensor service started");
        broadcastMessage(Constants.MESSAGE.LIGHTSENSOR_SERVICE_STARTED);
    }

    @Override
    protected void onServiceStopped() {
        Log.d(TAG, "light sensor service stopped");
        broadcastMessage(Constants.MESSAGE.LIGHTSENSOR_SERVICE_STOPPED);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "light sensor service onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onConnected() {
        super.onConnected();
        mClient.registerMessageReceiver(new MessageReceiver(Constants.MHLClientFilter.LIGHT_SENSOR) {
            @Override
            protected void onMessageReceived(JSONObject json) {
                Log.d(TAG, "Received LIGHT SENSOR update from server.");
                try {
                    JSONObject data = json.getJSONObject("data");
                    long timestamp = data.getLong("timestamp");
//                    double reading = data.getDouble("reading");
//                    Log.d(TAG, "Light Sensor updated at " + timestamp + " and the reading is " + reading);
                    int isSleep = data.getInt("isSleep");
                    Log.d(TAG, "Light Sensor updated at " + timestamp + " and isSleep is " + isSleep);
                    broadcastIsSleep(timestamp, isSleep);
                    /*
                    * if(isSleep)
                    *   if(timer is not running)
                    *       continue timer;
                    *       send broadcast to statactivity to continue timer
                    * else
                    *   if(timer is running)
                    *       stop timer
                    *       send broadcast to statactivity to stop timer;
                    * */
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            // Perform actions on light sensor data
            float reading = event.values[1];
//            Log.d(TAG, "Reading: " + reading);
//            Log.d(TAG, "Light sensor data received");

            // Hardcoding label
            int label = 1;
            LightSensorReading lightSensorReading = new LightSensorReading(mUserID, "MOBILE", "", System.currentTimeMillis(), label, reading);

            mClient.sendSensorReading(lightSensorReading);
        }else {
            // cannot identify sensor type
            Log.w(TAG, Constants.ERROR_MESSAGES.WARNING_SENSOR_NOT_SUPPORTED);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(TAG, "Accuracy changed: " + accuracy);
    }

    @Override
    protected void registerSensors() {
        //Registering light sensor
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void unregisterSensors() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this, mLightSensor);
        }
    }

    @Override
    protected int getNotificationID() {
        return Constants.NOTIFICATION_ID.LIGHT_SENSOR_SERVICE;
    }

    @Override
    protected String getNotificationContentText() {
        return getString(R.string.light_sensor_service_notification);
    }

    @Override
    protected int getNotificationIconResourceID() {
        return R.drawable.panda;
    }

    public void broadcastIsSleep(final long timestamp, final int isSleep) {
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY.TIMESTAMP, timestamp);
        intent.putExtra(Constants.KEY.IS_SLEEP, isSleep);
        intent.setAction(Constants.ACTION.BROADCAST_IS_SLEEP);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.sendBroadcast(intent);
    }
}
