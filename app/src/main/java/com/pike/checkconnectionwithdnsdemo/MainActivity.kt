package com.pike.checkconnectionwithdnsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        button.setOnClickListener {
            ConnectionCheckUtil.connectionCheck(object:ConnectionCheckUtil.OnConnectionCheckListener{
                override fun onConnected() {
                    textView.text = "connected"
                }

                override fun onDisconnected() {
                    textView.text = "disconnected"
                }
            })
        }
    }
}
