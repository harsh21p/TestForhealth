package com.example.forhealth
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.second_page.*
import java.io.IOException
import java.util.*

class SecondScreen : AppCompatActivity() {

    var EXTRA_DEVICE_ADDRESS = "device_address"

    private var pdeviceList = ArrayList<ModelForPairedDevices>()
//    private var ndeviceList = ArrayList<ModelForPairedDevices>()
    private val pdeviceadapter = PairedDeviceHolderAdapter(pdeviceList,this)
//    private val ndeviceadapter = PairedDeviceHolderAdapter(ndeviceList,this)
    companion object{
        lateinit var mBtAdapter: BluetoothAdapter
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket?=null
        var m_isConnected: Boolean = false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_page)
       scanButton.setOnClickListener(View.OnClickListener {
           Toast.makeText(applicationContext,"Started",Toast.LENGTH_LONG).show()
           doDiscovery()
       })
        var filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        this.registerReceiver(mReceiver, filter)
        filter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)

        val pdevicesRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewofpaireddevices)
//        val ndevicesRecyclerView = findViewById<RecyclerView>(R.id.newdevices)

//        ndevicesRecyclerView.layoutManager = LinearLayoutManager(this)
        pdevicesRecyclerView.layoutManager = LinearLayoutManager(this)
//        ndevicesRecyclerView.adapter = ndeviceadapter
        pdevicesRecyclerView.adapter = pdeviceadapter

        this.registerReceiver(mReceiver, filter)
        mBtAdapter = BluetoothAdapter.getDefaultAdapter()
        val pairedDevices = mBtAdapter!!.bondedDevices
        if (pairedDevices.size > 0) {
            for (device in pairedDevices) {
                var device = ModelForPairedDevices(device.name, device.address)
                pdeviceList.add(device)
                pdeviceadapter.notifyDataSetChanged()
            }
        } else {

            var device = ModelForPairedDevices("Not Found", "")
            pdeviceList.add(device)
            pdeviceadapter.notifyDataSetChanged()
        }
    }

    private fun doDiscovery() {
        if (mBtAdapter!!.isDiscovering) {
            mBtAdapter!!.cancelDiscovery()
        }
        mBtAdapter!!.startDiscovery()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mBtAdapter != null) {
            mBtAdapter!!.cancelDiscovery()
        }
        unregisterReceiver(mReceiver)
    }

    fun onItemClick(position: Int) {
        val m = pdeviceList[position]
        var address = m.getAddress().toString()
        intent = Intent(this, ThirdScreen::class.java)
        intent.putExtra(EXTRA_DEVICE_ADDRESS, address)
        connectTo(address)
       if(m_isConnected){
           Toast.makeText(applicationContext,"Connected",Toast.LENGTH_LONG).show()
       }
        else{
           Toast.makeText(applicationContext,"Failed to connect",Toast.LENGTH_LONG).show()
       }
    }

    private fun connectTo(address:String){

        try {
            if (m_bluetoothSocket == null || !m_isConnected) {
                mBtAdapter = BluetoothAdapter.getDefaultAdapter()
                val device: BluetoothDevice = mBtAdapter!!.getRemoteDevice(address)
                m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                m_bluetoothSocket!!.connect()
                m_isConnected=true
                startActivity(intent)

            }
        } catch (e: IOException) {
            m_isConnected=false
            Log.i(ContentValues.TAG,e.toString())
        }

    }
    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if(device!=null){
                        var device1 = ModelForPairedDevices(device!!.name, device!!.address)
//                        ndeviceList.add(device1)
//                        ndeviceadapter.notifyDataSetChanged()
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Toast.makeText(applicationContext,"Finished",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}


