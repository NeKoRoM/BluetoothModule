package com.nekorom.bt

import android.content.ClipData.Item
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        //initRcView()
        supportFragmentManager.beginTransaction().replace(R.id.placeHolder,DeviceListFragment()).commit()

    }
//    private fun    initRcView(){
//        val rcView = findViewById<RecyclerView>(R.id.rcViewPaired)
//        //rcView.layoutManager = LinearLayoutManager(this)
//        //val adapter = ItemAdapter()
//        rcView.adapter = adapter
//        adapter.submitList(createList())
//    }
//    private fun createList():List<ListItem>{
//        val list= ArrayList<ListItem>()
//        for (i in  0..5){
//            list.add(
//                ListItem(
//                    "deace name $i",
//                    "macsssssssssssss$i"
//                )
//
//
//            )
//        }
//        return list
//    }

}