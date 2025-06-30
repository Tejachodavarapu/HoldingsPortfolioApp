package com.tejachodavarapu.holdingsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.navigation.compose.rememberNavController
import com.teja.holdingsdemo.ui.theme.MyAppTheme
import com.tejachodavarapu.holdingsdemo.presentation.common.navigation.AppNavGraph
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tejachodavarapu.holdingsdemo.presentation.common.BottomNavBar
import com.tejachodavarapu.holdingsdemo.presentation.holdings.view.ProfitAndLossSummary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                            BottomNavBar(navController = navController)
                    },
                    contentWindowInsets = WindowInsets.systemBars
                ) { innerPadding ->
                    AppNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
