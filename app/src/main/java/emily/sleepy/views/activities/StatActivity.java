package emily.sleepy.views.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import emily.sleepy.R;
import emily.sleepy.services.LightSensorService;
import emily.sleepy.services.ServiceManager;

public class StatActivity extends AppCompatActivity {

    TextView phoneUsageTimer;
    Switch switchLightSensor;
    ServiceManager mServiceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        phoneUsageTimer =(TextView) findViewById(R.id.textViewPhoneUsageTimer);

        //obtain references to the on/off light sensor switches and handle the toggling appropriately
        switchLightSensor = (Switch) findViewById(R.id.switchLightSensor);
        switchLightSensor.setChecked(mServiceManager.isServiceRunning(LightSensorService.class));
        switchLightSensor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean enabled) {
                if (enabled){
//                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    mServiceManager.startSensorService(LightSensorService.class);
                }else{
                    mServiceManager.stopSensorService(LightSensorService.class);
                }
            }
        });
    }

}
