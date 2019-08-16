package padc.batch9.assignment3.broadcastreceiver;


import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.content.WakefulBroadcastReceiver;

import padc.batch9.assignment3.activity.TimerActivity;

public class AlarmReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //this will update the UI with message
        TimerActivity inst = TimerActivity.instance();
        inst.setAlarmText("Alarm! Wake up! Wake up!");

        Vibrator vibrator = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
//        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
//        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        if (alarmUri == null) {
//            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        }
//        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
//        ringtone.play();
//
//        //this will send a notification message
//        ComponentName comp = new ComponentName(context.getPackageName(),
//                AlarmService.class.getName());
//        startWakefulService(context, (intent.setComponent(comp)));
//        setResultCode(Activity.RESULT_OK);
    }
}
