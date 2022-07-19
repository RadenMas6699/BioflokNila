/*
 * Created by RadenMas on 22/4/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.bioflok_nila.ui.main

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.radenmas.bioflok_nila.R
import com.radenmas.bioflok_nila.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        b = ActivityMainBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        Firebase.messaging.subscribeToTopic("user")

        val versionName = packageManager.getPackageInfo(packageName, 0).versionName
        val packageName = packageManager.getPackageInfo(packageName, 0).packageName
        val appName = resources.getString(R.string.app_name)

        checkUpdate(appName, packageName, versionName)


        val dt = Date()
        when (dt.hours) {
            in 0..5 -> {
                b.tvTime.text = "Selamat Malam"
            }
            in 6..10 -> {
                b.tvTime.text = "Selamat Pagi"
            }
            in 11..14 -> {
                b.tvTime.text = "Selamat Siang"
            }
            in 15..18 -> {
                b.tvTime.text = "Selamat Sore"
            }
            in 19..23 -> {
                b.tvTime.text = "Selamat Malam"
            }
        }

        FirebaseDatabase.getInstance().reference.child("realtime")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                            val time = snapshot.child("time").value.toString().toLong()

                            val date = Date(time * 1000)
                            val formatClock = SimpleDateFormat("HH:mm zz")
                            val formatDate = SimpleDateFormat("dd MMM yyyy")

                            b.tvClock.text = formatClock.format(date)
                            b.tvDate.text = formatDate.format(date)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        val navController = findNavController(R.id.fragmentContainerView)
        b.navBottom.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            b.tvTitle.text = destination.label
        }
    }

    private fun checkUpdate(appName: String, packageName: String, versionName: String) {
        val defaultsRate = HashMap<String, Any>()
        defaultsRate["lates_app_version"] = versionName
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        val config: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        config.setConfigSettingsAsync(configSettings)
        config.setDefaultsAsync(defaultsRate)
        config.fetchAndActivate().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val newVersionCode = config.getString("lates_app_version")
                val lateAppVersion = newVersionCode.toFloat()
                if (lateAppVersion > versionName.toFloat()) {
                    showTheDialog(appName, packageName, newVersionCode)
                }
            }
        }
    }

    private fun showTheDialog(appName: String, packageName: String, versionName: String) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Update Aplikasi")
            .setMessage("Versi terbaru sudah tersedia! Update aplikasi $appName ke versi: $versionName")
            .setPositiveButton("Update", null)
            .show()

        dialog.setCancelable(false)

        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            val uriUpdate =
                "https://wa.me/6285298106699?text=Tolong%20dikirimkan%20updatenya%20aplikasi%20Bioflok%20Nila%20versi%20$versionName"
            val uri = Uri.parse(uriUpdate)
            val update = Intent(Intent.ACTION_VIEW, uri)
            update.setPackage("com.whatsapp")
            try {
                startActivity(update)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(uriUpdate)
                    )
                )
            }
        }
    }
}