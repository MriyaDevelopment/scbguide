package com.spravochnic.scbguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.spravochnic.scbguide.databinding.ActivityMainBinding
import java.lang.Exception
import kotlin.concurrent.thread
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fgMainContainer) as NavHostFragment).navController
    }

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}