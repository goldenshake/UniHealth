package com.example.unihealth



import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.app.NotificationCompat

class waterIntake : AppCompatActivity() {

    private lateinit var alarmManager: AlarmManager
    private lateinit var notificationManager: NotificationManager
    private lateinit var enterMinutes: EditText
    private lateinit var buttonAlarm: Button
    private val channelId= "ReminderChannel"
    private var notificationId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_intake)

        // Initialize views
        enterMinutes = findViewById(R.id.remindPT)
        buttonAlarm = findViewById(R.id.remindBT)

        // Initialize notification manager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()

        // Initialize alarm manager
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Set button click listener
        buttonAlarm.setOnClickListener {
            setRepeatingAlarm()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Reminder Channel"
            val descriptionText = "Channel for reminder notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setRepeatingAlarm() {
        val intervalMinutes = enterMinutes.text.toString().toLongOrNull() ?: return
        if (intervalMinutes <= 0) {
            // Show error message or handle invalid input
            return
        }

        val alarmIntervalMillis = intervalMinutes * 60 * 1000 // Convert minutes to milliseconds

        val alarmIntent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE)

        // Set repeating alarm to trigger every x minutes
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + alarmIntervalMillis, // Add the interval to the current time
            alarmIntervalMillis,
            pendingIntent
        )

        // Show notification after setting the alarm
        showNotification("Repeating Alarm Set", "Alarm will trigger every $intervalMinutes minute(s)")
    }

    fun showNotification(title: String, content: String) {
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.alarm_notification)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(notificationId++, notificationBuilder.build())
    }
}

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Display notification when the alarm triggers
        (context as waterIntake).showNotification("UniHealth", "It's time to drink some water!")
    }
}
