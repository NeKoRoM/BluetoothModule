package com.nekorom.bluetoothmodule

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nekorom.bluetoothmodule.databinding.FragmentMainBinding
import com.nekorom.bt.BluetoothConstants
import com.nekorom.bt.bluetooth.BluetoothController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment(), BluetoothController.Listener {

    private lateinit var btAdapter: BluetoothAdapter


    lateinit var bluetoothController: BluetoothController
    private lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtAdapter()
        val pref = activity?.getSharedPreferences(BluetoothConstants.PREFERENCES, Context.MODE_PRIVATE)
        val mac = pref?.getString(BluetoothConstants.MAC,"")
        bluetoothController = BluetoothController(btAdapter)
        binding.bList.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_deviceListFragment)
        }
        binding.bConnect.setOnClickListener {
            bluetoothController.connect(mac ?: "", this)
        }
        binding.sendSome.setOnClickListener {
            //bluetoothController.sendMessage("\u00010035A00??IDnumber\t\t\t\u0005051=\u0003")
            bluetoothController.sendMessage("T\u000D\u000A")

        }
    }
    private fun initBtAdapter(){
        val bManeger = activity?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = bManeger.adapter
    }

    override fun onReceive(message: String) {
        activity?.runOnUiThread{
            Log.d("btMess", message)
            when(message){
                BluetoothController.BLUETOOTH_CONNECTED ->{
                    binding.bConnect.backgroundTintList = AppCompatResources
                        .getColorStateList(requireContext(),R.color.green)
                    binding.tvStatus.text = message

                }
                BluetoothController.BLUETOOTH_NOT_CONNECTED ->{
                    binding.bConnect.backgroundTintList = AppCompatResources
                        .getColorStateList(requireContext(),R.color.red)
                    binding.tvStatus.text = message


                }
                else -> {
                    binding.tvStatus.text = message.substring(3,message.length)
                    Log.d("btMess", message)
                    val input = message
                    val regex = Regex("-?\\d+\\.?\\d*") // Регулярний вираз для знаходження десяткових чисел

                    val matches = regex.findAll(input)
                    val digitsOnly = StringBuilder()

                    for (match in matches) {
                        digitsOnly.append(match.value)
                    }
                    if(digitsOnly.toString().toDouble()>100){
                        binding.tvNext.text = "amoniak"

                    }else{
                        binding.tvNext.text = "mąka"

                    }

                }

            }
        }

    }


}