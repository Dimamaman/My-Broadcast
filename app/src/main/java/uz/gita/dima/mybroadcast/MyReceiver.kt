package uz.gita.dima.mybroadcast

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log

class MyReceiver : BroadcastReceiver() {

    private lateinit var mediaPlayer: MediaPlayer
    private val shared = MySharedPref.getInstance()

    override fun onReceive(context: Context?, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                val state = intent.getBooleanExtra("state", false)
                if (state) {
                    if (shared.planeOn) {
                        val mediaPlayer = MediaPlayer.create(context, R.raw.plane_on)
                        mediaPlayer.start()
                    }
                } else {
                    if (shared.planeOff) {
                        val mediaPlayer = MediaPlayer.create(context, R.raw.plane_off)
                        mediaPlayer.start()
                    }
                }
            }

            BluetoothAdapter.ACTION_STATE_CHANGED -> {
                when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
                    BluetoothAdapter.STATE_OFF -> {
                        if (shared.bluetoothOff) {
                            val mediaPlayer = MediaPlayer.create(context, R.raw.bluetooth_off)
                            mediaPlayer.start()
                        }
                    }

                    BluetoothAdapter.STATE_ON -> {
                        if (shared.bluetoothOn) {
                            val mediaPlayer = MediaPlayer.create(context, R.raw.bluetooth_on)
                            mediaPlayer.start()
                        }
                    }
                }
            }

            Intent.ACTION_HEADSET_PLUG -> {
                val int: Int = intent.getIntExtra("state", -1)
                if (shared.headPhonesOn && int == 1) {
                    val mediaPlayer = MediaPlayer.create(context, R.raw.headphones_on)
                    mediaPlayer.start()
                }

                if (shared.headPhonesOff && int == 0) {
                    val mediaPlayer = MediaPlayer.create(context, R.raw.headphones_off)
                    mediaPlayer.start()
                }
            }

            Intent.ACTION_SCREEN_ON -> {
                if (shared.screenOn) {
                    mediaPlayer = MediaPlayer.create(context, R.raw.screen_on)
                    mediaPlayer.start()
                }
            }

            Intent.ACTION_SCREEN_OFF -> {
                if (shared.screenOff) {
                    mediaPlayer = MediaPlayer.create(context, R.raw.screen_off)
                    mediaPlayer.start()
                }
            }

            Intent.ACTION_POWER_CONNECTED -> {
                if (shared.powerOn) {
                    mediaPlayer = MediaPlayer.create(context, R.raw.power_on)
                    mediaPlayer.start()
                }
            }

            Intent.ACTION_POWER_DISCONNECTED -> {
                if (shared.powerOff) {
                    mediaPlayer = MediaPlayer.create(context, R.raw.power_off)
                    mediaPlayer.start()
                }
            }
        }
    }
}