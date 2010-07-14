package org.nateperry.graphicpages;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class UpdateService extends Service {

    private static long UPDATE_INTERVAL_MS = 1 * 60 * 1000;

	@Override
	public void onCreate() {
		
	}

	@Override
	public void onStart(Intent intent, int startId) {
	    //handleCommand(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    //handleCommand(intent);

	    // We want this service to continue running until it is explicitly
	    // stopped, so return sticky.
	    return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public boolean onUnbind (Intent intent) {
		return super.onUnbind(intent);
	}

    static void schedule(Context context) {

        final Intent intent = new Intent(context, UpdateService.class);
        final PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);

        Calendar c = new GregorianCalendar();
        c.add(Calendar.HOUR, 2);

        final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pending);

        alarm.setRepeating(AlarmManager.RTC, c.getTimeInMillis(),
        		UPDATE_INTERVAL_MS, pending);
    }

}
