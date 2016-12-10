package emily.sleepy.constants;

/**
 * Created by kawo123 on 12/9/2016.
 */
public class Constants {

    /** Intent actions used to communicate between the main UI and the sensor service
     * @see android.content.Intent */
    public interface ACTION {
        String BROADCAST_MESSAGE = "edu.umass.cs.my-activities-toolkit.action.broadcast-message";
        String BROADCAST_STATUS = "edu.umass.cs.my-activities-toolkit.action.broadcast-status";
        String START_SERVICE = "edu.umass.cs.my-activities-toolkit.action.start-service";
        String STOP_SERVICE = "edu.umass.cs.my-activities-toolkit.action.stop-service";
        String NAVIGATE_TO_APP = "edu.umass.cs.my-activities-toolkit.action.navigate-to-app";
    }

    /**
     * Unique IDs associated with each service notification.
     */
    public interface NOTIFICATION_ID {
        int LIGHT_SENSOR_SERVICE = 101;
    }

    /** Keys to identify key-value data sent to/from the sensor service */
    public interface KEY {
        String SPECTROGRAM = "edu.umass.cs.my-activities-toolkit.key.spectrogram";
        String ACTIVITY = "edu.umass.cs.my-activities-toolkit.key.activity";
        String MESSAGE = "edu.umass.cs.my-activities-toolkit.key.message";
        String STATUS = "edu.umass.cs.my-activities-toolkit.key.status";
        String ACCELEROMETER_DATA = "edu.umass.cs.my-activities-toolkit.key.accelerometer-data";
        String TIMESTAMP = "edu.umass.cs.my-activities-toolkit.key.ppg-timestamp";
        String PPG_DATA = "edu.umass.cs.my-activities-toolkit.key.ppg-value";
        String HEART_RATE = "edu.umass.cs.my-activities-toolkit.key.heart-rate";
        String STEP_COUNT = "edu.umass.cs.my-activities-toolkit.key.step-count";
        String SPEAKER_NAME = "edu.umass.cs.my-activities-toolkit.key.speaker-name";
        String PPG_PEAK_TIMESTAMP = "edu.umass.cs.my-activities-toolkit.key.ppg-peak-timestamp";
        String PPG_PEAK_VALUE = "edu.umass.cs.my-activities-toolkit.key.ppg-peak-value";
        String ACCELEROMETER_PEAK_TIMESTAMP = "edu.umass.cs.my-activities-toolkit.key.accelerometer-peak-timestamp";
        String ACCELEROMETER_PEAK_VALUE = "edu.umass.cs.my-activities-toolkit.key.accelerometer-peak-value";
        String NOTIFICATION_ID = "edu.umass.cs.my-activities-toolkit.key.sensor-service-type";
    }

    /**
     * Messages sent to the main UI to update the status. These must be unique values.
     */
    public interface MESSAGE {
        int LIGHTSENSOR_SERVICE_STARTED = 0;
        int LIGHTSENSOR_SERVICE_STOPPED = 1;

    }

    /** Error/warning messages displayed to the user TODO: put into string resources */
    public interface ERROR_MESSAGES {
        String ERROR_NO_ACCELEROMETER = "ERROR: No accelerometer available...";
        String ERROR_NO_SENSOR_MANAGER = "ERROR: Could not retrieve sensor manager...";
        String WARNING_SENSOR_NOT_SUPPORTED = "WARNING: Sensor not supported!";
    }

    /** Timestamp-relevant constants */
    public interface TIMESTAMPS {
        long NANOSECONDS_PER_MILLISECOND = 1000000;
    }

    /**
     * Identifies common data filters used when receiving data from the server.
     * <br><br>
     * As an example, to listen for steps and activities detected, register a
     * message receiver object as follows:
     *
     * <pre>
     * {@code setMessageReceiver(new MessageReceiver(MessageReceiver.Filter.STEP_DETECTED, MessageReceiver.Filter.ACTIVITY_DETECTED) {
     *         @literal @Override
     *          void onMessageReceived(JSONObject json) {
     *              //parse json, handle message
     *          }
     *   });
     * }
     * </pre>
     */
    public interface MHLClientFilter {
        String LIGHT_SENSOR = "LIGHT_SENSOR";
    }
}
