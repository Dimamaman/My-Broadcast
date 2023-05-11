package uz.gita.dima.mybroadcast

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        MySharedPref.init(this)
    }
}