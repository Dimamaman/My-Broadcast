package uz.gita.dima.mybroadcast

import android.content.Context
import android.content.SharedPreferences

class MySharedPref private constructor(context: Context) {

    companion object {
        private lateinit var instance: MySharedPref

        fun init(context: Context) {
            instance = MySharedPref(context)
        }

        fun getInstance() = instance
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPref.edit()

    var screenOn: Boolean
    get() = sharedPref.getBoolean("screen_on",false)
    set(value) = editor.putBoolean("screen_on",value).apply()

    var screenOff: Boolean
        get() = sharedPref.getBoolean("screen_off",false)
        set(value) = editor.putBoolean("screen_off",value).apply()

    var powerOn: Boolean
        get() = sharedPref.getBoolean("power_on",false)
        set(value) = editor.putBoolean("power_on",value).apply()

    var powerOff: Boolean
        get() = sharedPref.getBoolean("power_off",false)
        set(value) = editor.putBoolean("power_off",value).apply()

    var planeOn: Boolean
        get() = sharedPref.getBoolean("plane_on",false)
        set(value) = editor.putBoolean("plane_on",value).apply()

    var planeOff: Boolean
        get() = sharedPref.getBoolean("plane_off",false)
        set(value) = editor.putBoolean("plane_off",value).apply()

    var bluetoothOn: Boolean
        get() = sharedPref.getBoolean("bluetooth_on",false)
        set(value) = editor.putBoolean("bluetooth_on",value).apply()

    var bluetoothOff: Boolean
        get() = sharedPref.getBoolean("bluetooth_off",false)
        set(value) = editor.putBoolean("bluetooth_off",value).apply()

    var headPhonesOn: Boolean
        get() = sharedPref.getBoolean("headPhones_on",false)
        set(value) = editor.putBoolean("headPhones_on",value).apply()

    var headPhonesOff: Boolean
        get() = sharedPref.getBoolean("headPhones_off",false)
        set(value) = editor.putBoolean("headPhones_off",value).apply()
}