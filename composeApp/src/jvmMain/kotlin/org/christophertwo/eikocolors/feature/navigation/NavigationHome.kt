package org.christophertwo.eikocolors.feature.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.christophertwo.eikocolors.core.common.RoutesHome
import org.christophertwo.eikocolors.feature.clients.presentation.ClientsRoot
import org.christophertwo.eikocolors.feature.works.presentation.WorksRoot
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavigationHome(
    navController: NavHostController,
    selectedRoute: RoutesHome = RoutesHome.Works,
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    NavHost(
        navController = navController,
        startDestination = RoutesHome.Works,
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        composable<RoutesHome.Works> {
            WorksRoot(
                viewModel = koinViewModel()
            )
        }
        composable<RoutesHome.Clients> {
            ClientsRoot(
                viewModel = koinViewModel()
            )
        }
    }

    LaunchedEffect(selectedRoute) {
        navController.navigate(selectedRoute) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}