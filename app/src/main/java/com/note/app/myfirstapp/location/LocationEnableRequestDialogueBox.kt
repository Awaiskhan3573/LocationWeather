package com.note.app.myfirstapp.location

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.view.Window
import com.note.app.myfirstapp.databinding.DialogPermissionBinding
import java.lang.Exception

class LocationEnableRequestDialogueBox(val mcontext: Context) : Dialog(mcontext) {
  lateinit var binding:DialogPermissionBinding
  @SuppressLint("CommitPrefEdits")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setCancelable(true)
    window!!.requestFeature(Window.FEATURE_NO_TITLE)
    window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    binding= DialogPermissionBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btnDialog.setOnClickListener {
      dismiss()
      try {
          val callGPSSettingIntent=Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        mcontext.startActivity(callGPSSettingIntent)
      }catch (e:Exception){

      }
    }

  }

}
