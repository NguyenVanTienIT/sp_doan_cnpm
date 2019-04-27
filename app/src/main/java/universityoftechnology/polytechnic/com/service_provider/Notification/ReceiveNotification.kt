package universityoftechnology.polytechnic.com.service_provider.Notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.firebase.messaging.RemoteMessage
import universityoftechnology.polytechnic.com.service_provider.Activity.HomeActivity
import android.app.PendingIntent
import android.R
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat.getSystemService




class ReceiveNotification : BroadcastReceiver() {
    var notificationManager : NotificationManager? = null
    var myNotification : Notification? = null
    companion object {
        val MY_NOTIFICATION_ID = 1
    }

    @SuppressLint("WrongConstant")
    override fun onReceive(context: Context?, intent: Intent?) {
        val myIntent = Intent(context, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            myIntent,
            Intent.FLAG_ACTIVITY_NEW_TASK
        )

        myNotification = NotificationCompat.Builder(context)
            .setContentTitle("Exercise of Notification!")
            .setContentText("Do Something...")
            .setTicker("Notification!")
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_lock_idle_alarm)
            .build()

        notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager!!.notify(MY_NOTIFICATION_ID, myNotification)
    }
}