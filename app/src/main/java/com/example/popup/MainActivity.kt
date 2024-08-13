package com.example.popup

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var notificationData: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set up fragments
        val fragmentOne = FragmentOne()
        val fragmentTwo = FragmentTwo()

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragmentOne)
            .add(R.id.fragment_container, fragmentTwo)
            .hide(fragmentTwo)
            .commit()

        fragmentOne.setCallback { data ->
            fragmentTwo.updateData(data)
            supportFragmentManager.beginTransaction()
                .hide(fragmentOne)
                .show(fragmentTwo)
                .commit()

            notificationData = data
            showNotification()
        }

        findViewById<Button>(R.id.show_notification_button).setOnClickListener {
            showNotification()
        }
    }

    private fun showNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("notification_data", notificationData)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, "default")
            .setContentTitle("Sample Notification")
            .setContentText(notificationData ?: "No data")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        // Customize menu item colors programmatically
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            item.icon?.setTint(ContextCompat.getColor(this, R.color.menu_item_color))
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Handle settings action
                true
            }
            R.id.action_about -> {
                // Handle about action
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
