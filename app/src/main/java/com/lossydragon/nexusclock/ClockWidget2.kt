package com.lossydragon.nexusclock

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.AlarmClock
import android.util.Log
import android.widget.RemoteViews

/* Class for the [Purple] Clock */
class ClockWidget2 : AppWidgetProvider() {

    companion object {
        private val TAG = ClockWidget2::class.java.simpleName
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (intent!!.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            val views = RemoteViews(context!!.packageName, R.layout.widget2)

            try {
                if (getAlarmIntent(context) != null) {
                    views.setOnClickPendingIntent(R.id.widget_clock, getAlarmIntent(context))
                }
            } catch (e: Throwable) {
                Log.e(TAG, "Could not launch Alarm app.", e)
            }

            AppWidgetManager
                .getInstance(context)
                .updateAppWidget(intent.getIntArrayExtra("appWidgetIds"), views)
        }

    }

    private fun getAlarmIntent(context: Context?): PendingIntent? {
        return PendingIntent.getActivity(context, 0, clockIntent(), 0)
    }

    private fun clockIntent(): Intent {
        val action =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                AlarmClock.ACTION_SHOW_ALARMS
            } else {
                AlarmClock.ACTION_SET_ALARM
            }

        return Intent(action).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
    }
}
