package com.nekorom.bt.bluetooth

import android.bluetooth.BluetoothAdapter

class BluetoothController(private val adapter: BluetoothAdapter) {
        private var connectThread: ConnectThread? =null


    fun connect(mac: String, listener: Listener){
        if (adapter.isEnabled && mac.isNotEmpty()){
            val device = adapter.getRemoteDevice(mac)
            connectThread = ConnectThread(device, listener)
            connectThread?.start()


        }
    }
    fun sendMessage(message: String){
        connectThread?.sendMessage(message)
    }
    fun closeConnection(){
        connectThread?.closeConnection()
    }

    interface Listener{
        fun onReceive(message: String)
    }
    companion object{
        const val BLUETOOTH_CONNECTED = "bluetooth_connected"
        const val BLUETOOTH_NOT_CONNECTED = "bluetooth_not_connected"

    }



}