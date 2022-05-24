package com.spravochnic.scbguide.ui.main

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.databinding.ActivityMainBinding
import com.spravochnic.scbguide.databinding.DialogErrorBinding
import com.spravochnic.scbguide.utils.gone
import com.spravochnic.scbguide.utils.visible
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun setStatusBarColor(@ColorRes statusBarColor: Int) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, statusBarColor)
    }
}