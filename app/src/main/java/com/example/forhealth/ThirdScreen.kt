package com.example.forhealth
import android.bluetooth.BluetoothSocket
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forhealth.R.id.*
import com.example.forhealth.SecondScreen.Companion.m_bluetoothSocket
import kotlinx.android.synthetic.main.third_page.*
import java.io.IOException
import java.lang.Exception
import java.util.*
class ThirdScreen : AppCompatActivity() {
    private var msgList = ArrayList<ModelMsgClass>()
    private val adapter = MsgAdaptor(msgList,this)
    private lateinit var msgRecyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_page)

        msgRecyclerView = findViewById<RecyclerView>(R.id.msgrecyclerview)
        msgRecyclerView.layoutManager = LinearLayoutManager(this)
        msgRecyclerView.adapter = adapter

        send.setOnClickListener(View.OnClickListener {
            var edit = findViewById<EditText>(R.id.search_field)
            var message = edit.text.toString()
            write(message.toByteArray(), m_bluetoothSocket!!)

        })

    }
    override fun onResume() {
        super.onResume()
        BluetoothServer(m_bluetoothSocket!!)
        msgRecyclerView.scrollToPosition(msgList.size - 1)
    }
    fun onItemClick(position: Int) {

    }
    private fun BluetoothServer(socket: BluetoothSocket){
         val inputStream = socket.inputStream
            try {
                val available = inputStream.available()
                val bytes = ByteArray(available)
                inputStream.read(bytes, 0, available)
                val text = String(bytes)
                var msg = ModelMsgClass(text)
                msgList.add(msg)
                adapter.notifyDataSetChanged()
                val splashscreentimeout = 2000
                Handler().postDelayed({
                    onResume()
                }, splashscreentimeout.toLong())

            } catch (e: Exception) {
                Log.e("client", "Cannot read data", e)
            }
    }

    private fun write(buffer: ByteArray?,socket: BluetoothSocket) {
        try {
            val mmOutStream = socket.outputStream
            mmOutStream.write(buffer)
            Toast.makeText(applicationContext,"Message sent",Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            Log.e(TAG, "Exception during write", e)
            Toast.makeText(applicationContext,"Failed to send!",Toast.LENGTH_LONG).show()
        }
    }
}
