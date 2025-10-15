package org.christophertwo.eikocolors

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import androidx.navigation.compose.rememberNavController
import org.christophertwo.eikocolors.core.common.PdfCreator
import org.christophertwo.eikocolors.core.common.RoutesStart
import org.christophertwo.eikocolors.core.ui.EikoColorsSystemTheme
import org.christophertwo.eikocolors.di.dataModule
import org.christophertwo.eikocolors.di.useCaseModule
import org.christophertwo.eikocolors.di.viewModelModule
import org.christophertwo.eikocolors.feature.navigation.NavigationStart
import org.koin.core.context.GlobalContext.startKoin


fun main() = application {
    startKoin {
        printLogger()
        modules(
            viewModelModule,
            useCaseModule,
            dataModule
        )
    }
    val windowState = rememberWindowState(
        placement = WindowPlacement.Maximized,
        position = WindowPosition.Aligned(Alignment.Center),
        width = 1200.dp,
        height = 800.dp
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "Eiko Colors System",
        state = windowState
    ) {
        val navControllerApp = rememberNavController()
        EikoColorsSystemTheme(
            isDarkTheme = true
        ) {
            NavigationStart(
                navController = navControllerApp,
                startDestination = RoutesStart.Home
            )
        }
    }
}
