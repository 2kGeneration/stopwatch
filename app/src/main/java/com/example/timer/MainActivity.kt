package com.example.timer
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.*
import android.view.Menu
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private lateinit var mHandler: Handler
    private var millisecondsRecord = 0L
    private var startTime = 0L
    private var timeBuff = 0L

    private val CHANNEL_ID = "channelID"
    private val CHANNEL_NAME = "channelName"
    private val NOTIF_ID = 0

    private val runnable = object : Runnable {
        override fun run() {
            millisecondsRecord = SystemClock.uptimeMillis() - startTime
            val accumulatedTime = timeBuff + millisecondsRecord
            val seconds = accumulatedTime / 1000 % 60
            val minutes = accumulatedTime / 1000 / 60
            val hours = seconds / 3600
            val time = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            chronometer.text = time
            mHandler.postDelayed(this, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotifChannel()


        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notif = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Notification")
            .setContentText("Hey, the session is over. Well done")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        val notifManager = NotificationManagerCompat.from(this)

        toNotification.setOnClickListener {
            notifManager.notify(NOTIF_ID, notif)
        }



        toStart.setOnClickListener {
            startTime = SystemClock.uptimeMillis()
            mHandler.postDelayed(runnable, 0)
            toRestore.isEnabled = false
            toStart.isEnabled = false
        }
        toPause.setOnClickListener {
            timeBuff += millisecondsRecord
            mHandler.removeCallbacks(runnable)
            toRestore.isEnabled = true
            toStart.isEnabled = true
        }
        toRestore.setOnClickListener {
            reset()
        }

        toMenu.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
        reset()
        mHandler = Handler(Looper.getMainLooper())

    }

    private fun createNotifChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun reset() {
        millisecondsRecord = 0L
        timeBuff = 0L
        chronometer.text = "45:00 session"
    }



}