package uz.gita.dima.mybroadcast

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer

class MyReceiver: BroadcastReceiver() {

    private lateinit var mediaPlayer: MediaPlayer
    private val shared = MySharedPref.getInstance()

    private var airPlaneListener: ((Int, String) -> Unit)? = null
    fun setAirPlaneListener(block: (Int, String) -> Unit) {
        airPlaneListener = block
    }

    private var bluetoothListener: ((Int, String) -> Unit)? = null
    fun setBluetoothListener(block: (Int,String) -> Unit) {
        bluetoothListener = block
    }

    private var headSetPlugListener: ((Int,String) -> Unit)? = null
    fun setHeadSetPluginListener(block: (Int, String) -> Unit) {
        headSetPlugListener = block
    }

    override fun onReceive(context: Context?, intent: Intent) {
        when(intent.action) {
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                val state = intent.getBooleanExtra("state",false)
                if (state) {
                    airPlaneListener?.invoke(1,Intent.ACTION_AIRPLANE_MODE_CHANGED)
                } else {
                    airPlaneListener?.invoke(0,Intent.ACTION_AIRPLANE_MODE_CHANGED)
                }
            }

            BluetoothAdapter.ACTION_STATE_CHANGED -> {
                when(val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
                    BluetoothAdapter.STATE_OFF -> {
                        bluetoothListener?.invoke(state,BluetoothAdapter.ACTION_STATE_CHANGED)
                    }

                    BluetoothAdapter.STATE_ON -> {
                        bluetoothListener?.invoke(state,BluetoothAdapter.ACTION_STATE_CHANGED)
                    }
                }
            }

            Intent.ACTION_HEADSET_PLUG -> {
                val int: Int = intent.getIntExtra("state", -1)
                if (int == 1) {
                    headSetPlugListener?.invoke(1,Intent.ACTION_HEADSET_PLUG)
                }
                if (int == 0) {
                    headSetPlugListener?.invoke(0,Intent.ACTION_HEADSET_PLUG)
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