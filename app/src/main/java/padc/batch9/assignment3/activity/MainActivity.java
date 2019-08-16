package padc.batch9.assignment3.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Calendar;

import padc.batch9.assignment3.R;

public class MainActivity extends AppCompatActivity {
    private String tag = getClass().getSimpleName();
    TextView tvSelectedContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSelectedContact = findViewById(R.id.tv_selected_contact);
    }

    public void onButtonSetTimerClick(View view) {
        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);
    }

    public void onButtonCalendarClick(View view) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");

        Calendar cal = Calendar.getInstance();
        long startTime = cal.getTimeInMillis();
        long endTime = cal.getTimeInMillis() + 60 * 60 * 1000;

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

        intent.putExtra(CalendarContract.Events.TITLE, "Assignment3 Event");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "This is a description");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Golden Gate Tower");
        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
        startActivity(intent);
        Toast.makeText(MainActivity.this, "Calendar Event Created!", Toast.LENGTH_LONG).show();
    }

    public void onButtonGetVideoClicl(View view) {
        // initiate a video view
        VideoView simpleVideoView = (VideoView) findViewById(R.id.vv_video);
        simpleVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.fish));
        simpleVideoView.start();
    }

    public void onButtonGetContactClick(View view) {
        Uri uri = Uri.parse("content://contacts");
        Intent intent = new Intent(Intent.ACTION_PICK, uri);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, 11);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String[] projection = { ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

                Cursor cursor = getContentResolver().query(uri, projection,
                        null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex);

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);
                tvSelectedContact.setText("Selected Contact:"+name);

                Log.d(tag, "ZZZ number : " + number +" , name : "+name);
                cursor.close();
            }
        }
    }

    public void onButtonWebSearchtClick(View view) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        String keyword= "Lover Lyrics, Taylor Swift";
        intent.putExtra(SearchManager.QUERY, keyword);
        startActivity(intent);
    }
}
