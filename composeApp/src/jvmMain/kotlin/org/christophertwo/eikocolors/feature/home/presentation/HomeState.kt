package org.christophertwo.eikocolors.feature.home.presentation

import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Boxes
import compose.icons.fontawesomeicons.solid.Home
import compose.icons.fontawesomeicons.solid.User
import compose.icons.fontawesomeicons.solid.Users
import org.christophertwo.eikocolors.core.common.RoutesHome

data class HomeState(
    val navItems: List<Pair<RoutesHome, ImageVector>> = listOf(
        RoutesHome.Works to FontAwesomeIcons.Solid.Boxes,
        RoutesHome.Clients to FontAwesomeIcons.Solid.Users,
    ),
    val selectedNavItem: RoutesHome = RoutesHome.Works
)