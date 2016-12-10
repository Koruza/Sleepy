package emily.sleepy.services;

import org.json.JSONException;
import org.json.JSONObject;

import edu.umass.cs.MHLClient.sensors.SensorReading;

/**
 * Created by kawo123 on 12/9/2016.
 */
public class LightSensorReading extends SensorReading {

    private final double reading;
    private final int label;

    public LightSensorReading(String userID, String deviceType, String deviceID, long t, int label, float reading){
        super(userID, deviceType, deviceID, "SENSOR_LIGHT", t, label);
        this.reading = (double) reading;
        this.label = label;
    }

    @Override
    protected JSONObject toJSONObject() {
        JSONObject obj = getBaseJSONObject();
        JSONObject data = new JSONObject();

        try {
            data.put("t", timestamp);
            data.put("reading", reading);
            data.put("label", label);
            obj.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
