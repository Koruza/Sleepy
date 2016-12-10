package emily.sleepy.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;
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
        // time and date
        String[] newHistory = new String[history.size()];
        for (int i = history.size()-1; i >= 0; i--){
            long date = history.get(i).startTime;
            Date d = new Date(date);
            newHistory[history.size()-i-1] = d + "\n" + Long.toString(history.get(i).duration/1000)+" Seconds";
        }
        if (history.size() >0) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, newHistory);
            ListView listOfHistory = (ListView) findViewById(R.id.listViewHistory);
            listOfHistory.setAdapter(arrayAdapter);
            listOfHistory.setTextFilterEnabled(true);
        }
//        Log.d(TAG, "" + history.get(history.size() - 1).startTime);
//        Log.d(TAG, "" + history.get(history.size() - 1).duration);

    }

}