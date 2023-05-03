package com.example.baseproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baseproject.base.extension.parcelable
import com.example.baseproject.base.model.NetworkException
import com.example.baseproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val BROADCAST_NETWORK_EXCEPTION_ACTION = "ACTION_NETWORK_EXCEPTION"
        const val BROADCAST_NETWORK_EXCEPTION_EXTRA = "EXTRA_NETWORK_EXCEPTION"
    }

    private lateinit var mBinding: ActivityMainBinding
    private val mOnNetworkExceptionReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.parcelable<NetworkException>(BROADCAST_NETWORK_EXCEPTION_EXTRA)
                    ?.let { networkException ->
                        val backStackEntry = try {
                            // show dialog error
                        } catch (t: Throwable) {
                            null
                        }
                        // Prevent duplicate Fragments in Back Stack
                        if (backStackEntry == null) {
                            // show dialog error
                        }
                    }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onResume() {
        registerReceiver(
            mOnNetworkExceptionReceiver,
            IntentFilter(BROADCAST_NETWORK_EXCEPTION_ACTION)
        )
        super.onResume()
    }

    override fun onPause() {
        unregisterReceiver(mOnNetworkExceptionReceiver)
        super.onPause()
    }
}