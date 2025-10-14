package org.christophertwo.eikocolors.feature.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.christophertwo.eikocolors.core.common.RoutesStart
import org.christophertwo.eikocolors.feature.home.presentation.HomeRoot
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavigationStart(
    navController: NavHostController,
    startDestination: RoutesStart,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<RoutesStart.Home> {
            HomeRoot(
                viewModel = koinViewModel()
            )
        }
    }
}