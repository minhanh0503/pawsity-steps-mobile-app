package com.example.eecs4443_team7_project.util

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.eecs4443_team7_project.R

/**
 * NotificationReceiver is a BroadcastReceiver that handles incoming notifications.
 */
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Pawsitive Steps"
        val message = intent.getStringExtra("message") ?: "Time to check in on your pet!"

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        val notification = NotificationCompat.Builder(context, "daily_reminder")
            .setSmallIcon(R.drawable.ic_paw)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}
