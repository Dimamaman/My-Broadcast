package uz.gita.dima.mybroadcast

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        MySharedPref.init(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}