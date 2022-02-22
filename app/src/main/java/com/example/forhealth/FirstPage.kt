package com.example.forhealth

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.first_page.*

class FirstPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_page)

        secondpagebtn.setOnClickListener(View.OnClickListener {
                val firstIntent = Intent(this, SecondScreen::class.java)
                startActivity(firstIntent)
                finish()
        })
    }

    override fun onResume() {
        super.onResume()
        val timeout = 500
        Handler().postDelayed({
            checkBluetoothState()
        }, timeout.toLong())
    }

    private fun checkBluetoothState(){
        var bAdapter = BluetoothAdapter.getDefaultAdapter()
        if(bAdapter.isEnabled){
            bluetoothswitch.isChecked = true
            bluetoothonoffmsg.text="Bluetooth is on"
            bletoothicon.setImageResource(R.drawable.ic_baseline_bluetooth_24)
            secondpagebtn.isEnabled=true
        }else {
            bluetoothswitch.isChecked = false
            bluetoothonoffmsg.text="Bluetooth is off"
            bletoothicon.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24)
            secondpagebtn.isEnabled=false
        }
        bluetoothswitch.setOnClickListener(View.OnClickListener {
            if (bAdapter.isEnabled){
                bAdapter.disable()
                onResume()
            }else{
                var bluetoothintent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivity(bluetoothintent)
                onResume()
            }
        })
    }
}