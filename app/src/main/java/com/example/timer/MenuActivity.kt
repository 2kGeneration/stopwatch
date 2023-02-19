package com.example.timer

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_menu.*
import kotlin.system.exitProcess

class MenuActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        toExit.setOnClickListener {
            val alertdialog: AlertDialog = AlertDialog.Builder(this).create()
            alertdialog.setTitle("Exit Dialog")
            alertdialog.setTitle("do you want exit?")
            alertdialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes") { dialog, which ->
                this.finishAffinity()

            }
            alertdialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { dialog, which ->
                dialog.dismiss()
            }
            alertdialog.show()
        }

        toHistory.setOnClickListener {
            val alertdialog: AlertDialog = AlertDialog.Builder(this).create()
            alertdialog.setTitle("Coming soon")
            alertdialog.setButton(AlertDialog.BUTTON_POSITIVE, "Got it") { dialog, which ->
                dialog.dismiss()
            }
            alertdialog.show()
        }


        toInstagram.setOnClickListener {
            val url = "https://www.instagram.com/nowakkines/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)



        }


    }
}

//        var btn : TextView = findViewById<TextView>(R.id.toExit)
//
//        btn.setOnClickListener {
//            val alertdialog : AlertDialog = AlertDialog.Builder(this).create()
//            alertdialog.setTitle("Exit Dialog")
//            alertdialog.setTitle("do you want exit?")
//
//            alertdialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes") {
//                dialog, which -> finish()
//                dialog.dismiss()
//
//                alertdialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No") {
//                    dialog, which ->
//                    dialog.dismiss()
//                }
//                alertdialog.show()
//            }
//
//        }
//
//    }