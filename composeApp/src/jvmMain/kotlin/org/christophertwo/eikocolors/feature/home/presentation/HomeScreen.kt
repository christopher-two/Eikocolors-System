package org.christophertwo.eikocolors.feature.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import org.christophertwo.eikocolors.core.ui.EikoColorsSystemTheme
import org.christophertwo.eikocolors.feature.navigation.NavigationHome
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeRoot(
    viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    val navControllerHome = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                NavigationRail(
                    containerColor = colorScheme.surfaceContainer,
                    contentColor = colorScheme.onSurface,
                    modifier = Modifier.fillMaxHeight(),
                    content = {
                        state.navItems.forEach { item ->
                            NavigationRailItem(
                                selected = item.first == state.selectedNavItem,
                                onClick = { onAction(HomeAction.SelectItem(item.first)) },
                                icon = {
                                    Icon(
                                        imageVector = item.second,
                                        contentDescription = null,
                                        modifier = Modifier.size(30.dp)
                                    )
                                },
                                modifier = Modifier.padding(top = 4.dp),
                                label = {
                                    Text(
                                        text = item.first.displayName,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = 12.sp
                                        )
                                    )
                                }
                            )
                        }
                    }
                )
                NavigationHome(
                    navController = navControllerHome,
                    selectedRoute = state.selectedNavItem,
                    paddingValues = paddingValues
                )
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    EikoColorsSystemTheme {
        HomeScreen(
            state = HomeState(),
            onAction = {}
        )
    }
}