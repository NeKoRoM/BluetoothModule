package com.nekorom.bt.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.nio.charset.Charset
import java.util.UUID

class ConnectThread(
    private val device: BluetoothDevice,
    val listener: BluetoothController.Listener
) : Thread() {
    private val uuid = "00001101-0000-1000-8000-00805F9B34FB"
    private var mSocket: BluetoothSocket? = null

    private val w1251: Charset = charset("Windows-1251")


    init {
        try {
            mSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid))
        } catch (e: IOException) {

        } catch (se: SecurityException) {


        }
    }

    override fun run() {
        try {

            // Log.d("BtMyLog", "connecting...")

            mSocket?.connect()
            listener.onReceive(BluetoothController.BLUETOOTH_CONNECTED)
            readMessage()
            // Log.d("BtMyLog", "connected")

        } catch (e: IOException) {
            // Log.d("BtMyLog", "not connected 'IO exception'")
            listener.onReceive(BluetoothController.BLUETOOTH_NOT_CONNECTED)


            //todo inform user io exception (lost connection or some problem in connect)
        } catch (se: SecurityException) {


        }

    }

    private fun readMessage() {
        val buffer = ByteArray(4096)

        while (true) {
            try {
                val length = mSocket?.inputStream?.read(buffer)
                val message = String(buffer, offset = 0, length ?: 0, w1251)
                listener.onReceive(message)
                Log.d("btMess", message)

            } catch (e: IOException) {
                listener.onReceive(BluetoothController.BLUETOOTH_NOT_CONNECTED)
                break
            }
        }
    }

    fun sendMessage(message: String){
        try {

            mSocket?.outputStream?.write(message.toByteArray(w1251))
            Log.d("SendBtMess", message)
        }catch (e: IOException){
            listener.onReceive(BluetoothController.BLUETOOTH_NOT_CONNECTED)
        }
    }

    fun closeConnection() {
        try {
            mSocket?.close()
        } catch (e: IOException) {

        }
    }

}