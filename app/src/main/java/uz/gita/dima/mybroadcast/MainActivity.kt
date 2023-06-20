package uz.gita.dima.mybroadcast

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import uz.gita.dima.mybroadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val myAdapter = MyAdapter()
    private val shared = MySharedPref.getInstance()
    private lateinit var binding: ActivityMainBinding
    private lateinit var layout: View

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        layout = binding.rv


        when {
            ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                layout.showSnackBar(
                    binding.root,
                    getString(R.string.permission_granted),
                    Snackbar.LENGTH_SHORT,
                    null
                ) {

                }
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.FOREGROUND_SERVICE
            ) -> {
                layout.showSnackBar(
                    binding.root,
                    getString(R.string.permission_required),
                    Snackbar.LENGTH_SHORT,
                    getString(R.string.ok)
                ) {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        myAdapter.submitList(list)
        binding.rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.rv.adapter = myAdapter

        binding.apply {
            toolbar.title = "Events List"
            toolbar.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }


        myAdapter.setClickChecked {

            val intent = Intent(this@MainActivity, MyService::class.java)
            intent.putExtra("COMMAND", it.action)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else startService(intent)

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
}

fun View.showSnackBar(
    view: View,
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackbar = Snackbar.make(view, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action(this)
        }.show()
    } else {
        snackbar.show()
    }
}