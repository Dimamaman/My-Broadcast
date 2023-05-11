package uz.gita.dima.mybroadcast

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import uz.gita.dima.mybroadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val receiver = MyReceiver()
    private val myAdapter = MyAdapter()
    private val shared = MySharedPref.getInstance()
    private lateinit var binding: ActivityMainBinding
    private lateinit var intentFilter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        myAdapter.submitList(list)
        binding.rv.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        binding.rv.adapter = myAdapter

        binding.apply {
            toolbar.title = "Events List"
            toolbar.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }

        intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_HEADSET_PLUG)
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        }

        myAdapter.setClickChecked {
            when (it.action) {
                Intent.ACTION_SCREEN_ON -> {
                    shared.screenOn = it.isChecked
                }

                Intent.ACTION_SCREEN_OFF -> {
                    shared.screenOff = it.isChecked
                }

                Intent.ACTION_POWER_CONNECTED -> {
                    shared.powerOn = it.isChecked
                }

                Intent.ACTION_POWER_DISCONNECTED -> {
                    shared.powerOff = it.isChecked
                }

                1.toString() -> {
                    shared.planeOn = it.isChecked
                }

                0.toString() -> {
                    shared.planeOff = it.isChecked
                }

                "HeadPhones On" -> {
                    shared.headPhonesOn = it.isChecked
                }

                "HeadPhones Off" -> {
                    shared.headPhonesOff = it.isChecked
                }

                BluetoothAdapter.STATE_ON.toString() -> {
                    shared.bluetoothOn = it.isChecked
                }
                BluetoothAdapter.STATE_OFF.toString() -> {
                    shared.bluetoothOff = it.isChecked
                }
            }
        }

        receiver.setAirPlaneListener { number, action ->
            if (shared.planeOn && number == 1) {
                val mediaPlayer = MediaPlayer.create(this, R.raw.plane_on)
                mediaPlayer.start()
            }
            if (shared.planeOff && number == 0) {
                val mediaPlayer = MediaPlayer.create(this, R.raw.plane_off)
                mediaPlayer.start()
            }
        }

        receiver.setHeadSetPluginListener { number, action ->
            if (shared.headPhonesOn && number == 1) {
                val mediaPlayer = MediaPlayer.create(this, R.raw.headphones_on)
                mediaPlayer.start()
            }
            if (shared.headPhonesOff && number == 0) {
                val mediaPlayer = MediaPlayer.create(this, R.raw.headphones_off)
                mediaPlayer.start()
            }
        }


        receiver.setBluetoothListener { number, string ->
            if (shared.bluetoothOn && number == 12) {
                val mediaPlayer = MediaPlayer.create(this, R.raw.bluetooth_on)
                mediaPlayer.start()
            }
            if (shared.bluetoothOff && number == 10) {
                val mediaPlayer = MediaPlayer.create(this, R.raw.bluetooth_off)
                mediaPlayer.start()
            }
        }
        register()
    }

    private fun register() {
        registerReceiver(receiver, intentFilter)
    }

    private val list = listOf(
        EventData(
            0,
            "Screen On",
            Intent.ACTION_SCREEN_ON,
            R.drawable.screen_on_1,
            R.drawable.screen_on,
            isChecked = shared.screenOn
        ),
        EventData(
            1,
            "Screen Off",
            Intent.ACTION_SCREEN_OFF,
            R.drawable.screen_off_1,
            R.drawable.screen_off,
            isChecked = shared.screenOff
        ),
        EventData(
            2,
            "Power On",
            Intent.ACTION_POWER_CONNECTED,
            R.drawable.power_on_1,
            R.drawable.power_on,
            isChecked = shared.powerOn
        ),
        EventData(
            3,
            "Power Off",
            Intent.ACTION_POWER_DISCONNECTED,
            R.drawable.power_off_1,
            R.drawable.power_off,
            isChecked = shared.powerOff
        ),

        EventData(
            4,
            "Bluetooth on",
            BluetoothAdapter.STATE_ON.toString(),
            R.drawable.bluetooth_on_1,
            R.drawable.bluetooth_on,
            isChecked = shared.bluetoothOn
        ),
        EventData(
            5,
            "Bluetooth off",
            BluetoothAdapter.STATE_OFF.toString(),
            R.drawable.bluetooth_off_1,
            R.drawable.bluetooth_off,
            isChecked = shared.bluetoothOff
        ),

        EventData(
            6,
            "Plane On",
            1.toString(),
            R.drawable.plane_on_1,
            R.drawable.plane_on,
            isChecked = shared.planeOn
        ),

        EventData(
            7,
            "Plane Off",
            0.toString(),
            R.drawable.plane_off_1,
            R.drawable.plane_off,
            isChecked = shared.planeOff
        ),

        EventData(
            8,
            "HeadPhones On",
            "HeadPhones On",
            R.drawable.headphones_on_1,
            R.drawable.headphones_on,
            isChecked = shared.headPhonesOn
        ),

        EventData(
            8,
            "HeadPhones Off",
            "HeadPhones Off",
            R.drawable.headphones_off_1,
            R.drawable.headphones_off,
            isChecked = shared.headPhonesOff
        )
    )

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}