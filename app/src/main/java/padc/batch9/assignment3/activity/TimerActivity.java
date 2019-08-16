package padc.batch9.assignment3.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import padc.batch9.assignment3.R;
import padc.batch9.assignment3.broadcastreceiver.AlarmReceiver;

public class TimerActivity extends AppCompatActivity {
    private String tag = getClass().getSimpleName();

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker tpTimePicker;
    private static TimerActivity instance;
    private TextView tvAlarmText, tvSetUpTime;
    boolean isTimerButtonClicked = true;
    Button btnTimer;

    public static TimerActivity instance() {
        return instance;
    }

    @Override
    protected void onStart() {
        super.onStart();
        instance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        tpTimePicker = (TimePicker) findViewById(R.id.tp_timepicker);
        tvAlarmText = (TextView) findViewById(R.id.tv_alarm_text);
        tvSetUpTime = (TextView) findViewById(R.id.tv_setup_time);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        btnTimer = findViewById(R.id.btn_timer);
    }


    public void onTimerButtonClick(View view) {
        if (isTimerButtonClicked) {
            Log.d("MyActivity", "Alarm On");
            isTimerButtonClicked = false;
            btnTimer.setText("Stop");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, tpTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, tpTimePicker.getCurrentMinute());
            tvSetUpTime.setText("Timer:"+tpTimePicker.getCurrentHour()+":"+tpTimePicker.getCurrentMinute());
            Intent myIntent = new Intent(TimerActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(TimerActivity.this, 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.RTC_WAKEUP, pendingIntent);
        } else {
            isTimerButtonClicked = true;
            btnTimer.setText("Start");
            alarmManager.cancel(pendingIntent);
            setAlarmText("");
            Log.d("MyActivity", "Alarm Off");
        }
    }

    public void setAlarmText(String alarmText) {
        tvAlarmText.setText(alarmText);
    }
}
