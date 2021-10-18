package com.development.myvariousintent

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.development.myvariousintent.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val phNo = "6297317879"
    private val latitude = "22.4815"
    private val longitude = "88.3864"
    private var webUrl = "https://www.youtube.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btCall.onClick()
        binding.btLocation.onClick()
        binding.btWebsite.onClick()
        binding.btMsg.onClick()
        binding.btWhatsapp.onClick()
    }

    private fun View.onClick() {
        this.setOnClickListener {
            when (it.id) {
                binding.btCall.id -> {
                    val intent = Intent()
                    intent.action = Intent.ACTION_DIAL
                    intent.data = Uri.parse("tel:$phNo")
                    startActivity(intent)
                }
                binding.btLocation.id -> {
                    val gmmIntentUri = Uri.parse("google.navigation:q=$latitude,$longitude")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                }
                binding.btWebsite.id -> {
                    if (!webUrl.contains("http://") && !webUrl.contains("https://"))
                        webUrl = "https://$webUrl"
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
                    startActivity(browserIntent)
                }
                binding.btMsg.id -> {
                    val smsIntent = Intent(Intent.ACTION_VIEW)

                    smsIntent.data = Uri.parse("smsto:")
                    smsIntent.type = "vnd.android-dir/mms-sms"
                    smsIntent.putExtra("address", phNo)
                    smsIntent.putExtra("sms_body", "Test ")

                    try {
                        startActivity(smsIntent)
                        finish()
                        Log.i("Finished sending SMS...", "")
                    } catch (ex: ActivityNotFoundException) {
                        Toast.makeText(
                            this@MainActivity,
                            "SMS faild, please try again later.", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                binding.btWhatsapp.id -> {
                     val url = "https://api.whatsapp.com/send?phone=$phNo"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }
        }
    }
}