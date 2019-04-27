package universityoftechnology.polytechnic.com.service_provider.Notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.content.Context.NOTIFICATION_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.app.NotificationManager
import android.R
import android.media.RingtoneManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import universityoftechnology.polytechnic.com.service_provider.Activity.HomeActivity
import universityoftechnology.polytechnic.com.service_provider.Activity.MainActivity



class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // ...

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("Notification", "From: ${remoteMessage?.from}")

        // Check if message contains a data payload.
      /*  remoteMessage?.data?.isNotEmpty()?.let {
            Log.d("Notification", "Message data payload: " + remoteMessage.data)

            if (true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow()
            }
        }*/

        // Check if message contains a notification payload.
       /* remoteMessage?.notification?.let {
            Log.d("Notification", "Message Notification Body: ${it.body}")
        }*/

        var click_action = remoteMessage!!.notification!!.clickAction
        if (click_action == null) click_action = "universityoftechnology.polytechnic.com.service_provider_TARGET_NOTIFICATION"
        Log.d("Notification", click_action+"==========")

        sendNotification(remoteMessage!!.notification!!.title.toString(), remoteMessage!!.notification!!.body.toString(), click_action!!)
    }

    private fun sendNotification(title: String, messageBody: String, click_action : String) {
        val intent = Intent(click_action)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.sym_def_app_icon)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notificationBuilder.build())

       /* val notifyIntent = Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, "HOME").apply {
            setContentIntent(notifyPendingIntent)

        }
        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }*/
    }
}