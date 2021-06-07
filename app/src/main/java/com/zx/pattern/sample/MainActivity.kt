package com.zx.pattern.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zx.pattern.sample.databinding.ActivityMainBinding
import com.zx.pattern.sample.model.ConfigInfo

class MainActivity : AppCompatActivity() {
    var zxContainer:ZxContainer? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        zxContainer?.let {
            it.onDestroy()
            binding.rootContainer.removeAllViews()
        }



        zxContainer = ZxContainer(this)

        zxContainer?.setupBoard(binding.rootContainer)
        zxContainer?.onCreate()

    }

    override fun onResume() {
        super.onResume()
        zxContainer?.onResume()
    }

    override fun onPause() {
        super.onPause()
        zxContainer?.onPause()
    }

    override fun onStart() {
        super.onStart()
        zxContainer?.onStart()
    }

    override fun onStop() {
        super.onStop()
        zxContainer?.onStop()
    }
}