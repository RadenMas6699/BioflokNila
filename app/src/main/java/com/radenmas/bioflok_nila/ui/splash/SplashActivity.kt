/*
 * Created by RadenMas on 22/4/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.bioflok_nila.ui.splash

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
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.radenmas.bioflok_nila.R
import com.radenmas.bioflok_nila.databinding.ActivitySplashBinding
import com.radenmas.bioflok_nila.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var b: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        b = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(b.root)

        val versionName = packageManager.getPackageInfo(packageName, 0).versionName
        val packageName = packageManager.getPackageInfo(packageName, 0).packageName
        val appName = resources.getString(R.string.app_name)
        b.tvAppVersion.text = resources.getString(R.string.app_version, versionName)

        checkUpdate(appName, packageName, versionName)
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
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }, 1500)
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