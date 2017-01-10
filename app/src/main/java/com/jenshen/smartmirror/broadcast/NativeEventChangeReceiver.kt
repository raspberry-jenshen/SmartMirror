package com.jenshen.smartmirror.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jenshen.smartmirror.app.SmartMirrorApp
import com.jenshen.smartmirror.service.StartMirrorService

class NativeEventChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val preferencesManager = SmartMirrorApp.rootComponent.providePreferencesManager()
        if (preferencesManager.isMirror()) {
            context.startService(Intent(context, StartMirrorService::class.java))
        }
    }
}