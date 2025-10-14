package org.christophertwo.eikocolors.feature.home.presentation

import org.christophertwo.eikocolors.core.common.RoutesHome

sealed interface HomeAction {
    data class SelectItem(val item: RoutesHome) : HomeAction
}