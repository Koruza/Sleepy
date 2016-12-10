package emily.sleepy.constants;

/**
 * Created by kawo123 on 12/9/2016.
 */
public class Constants {

    /** Intent actions used to communicate between the main UI and the sensor service
     * @see android.content.Intent */
    public interface ACTION {
        String BROADCAST_MESSAGE = "emily.sleepy.action.broadcast-message";
        String BROADCAST_STATUS = "emily.sleepy.action.broadcast-status";
        String START_SERVICE = "emily.sleepy.action.start-service";
        String STOP_SERVICE = "emily.sleepy.action.stop-service";
        String NAVIGATE_TO_APP = "emily.sleepy.action.navigate-to-app";
        String BROADCAST_IS_SLEEP = "emily.sleepy.action.is-sleep";
    }

    /**
     * Unique IDs associated with each service notification.
     */
    public interface NOTIFICATION_ID {
        int LIGHT_SENSOR_SERVICE = 101;
    }

    /** Keys to identify key-value data sent to/from the sensor service */
    public interface KEY {
        String MESSAGE = "emily.sleepy.key.message";
        String STATUS = "emily.sleepy.key.status";
        String TIMESTAMP = "emily.sleepy.key.ppg-timestamp";
        String IS_SLEEP = "emily.sleepy.key.is-sleep";
        String NOTIFICATION_ID = "emily.sleepy.key.sensor-service-type";
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
