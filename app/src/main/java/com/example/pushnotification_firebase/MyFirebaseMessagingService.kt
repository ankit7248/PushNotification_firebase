package com.example.pushnotification_firebase
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelID = "Notification_channel"
const val channelName = "com.example.pushnotification_firebase"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // generate the notification
    // attach the notification created with the custom layout

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.getNotification() != null){
            generateNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)
        }
    }
    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView = RemoteViews("com.example.notification_firebase", R.layout.notification)

        remoteView.setTextViewText(R.id.Title, title)
        remoteView.setTextViewText(R.id.message, message)
        remoteView.setImageViewResource(R.id.Image_Ankit, R.drawable.ankit)
        
        return remoteView
    }

    fun generateNotification(title: String, message: String) {



        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // it will clear the all ctivity stack and put that activity MyFirebaseMessagingService


        val pendingIntent = PendingIntent.getActivity(
            this@MyFirebaseMessagingService,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )


        // PendingIntent.FLAG_ONE_SHOT -> when the user click in notification then activity will not destroy it will open one times the activity.

        //channel name , channel Id

        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, channelID)
                .setSmallIcon(R.drawable.ankit)
                .setAutoCancel(true)
                .setVibrate(
                    longArrayOf(
                        1000,
                        1000,
                        1000,
                        1000
                    )
                ) // there is a order in array like In first index it will vibrate then in 2 index it will relax again same thing happen
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, message))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationsChannel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationsChannel)
        }
        notificationManager.notify(0, builder.build())
    }

}