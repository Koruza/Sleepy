package emily.sleepy.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import emily.sleepy.R;
import emily.sleepy.util.History;
import emily.sleepy.util.Session;

public class HistoryActivity extends AppCompatActivity {

    List<Session> history = History.getInstance();

    private static final String TAG = HistoryActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Log.d(TAG, "" + history.get(history.size() - 1).startTime);
        Log.d(TAG, "" + history.get(history.size() - 1).duration);

    }

}