package emily.sleepy.views.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import emily.sleepy.R;

public class StatActivity extends AppCompatActivity {

    TextView phoneUsageTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

//        phoneUsageTimer =(TextView) findViewById(R.id.textViewPhoneUsageTimer);
        TextView t = (TextView) findViewById(R.id.textViewPhoneUsageTimer);
        t.setText(String.format(Locale.getDefault(),"Hi"));
    }

}
