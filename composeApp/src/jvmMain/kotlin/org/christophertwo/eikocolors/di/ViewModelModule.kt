package org.christophertwo.eikocolors.di

import org.christophertwo.eikocolors.feature.home.presentation.HomeViewModel
import org.christophertwo.eikocolors.feature.works.presentation.WorksViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule
    get() = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::WorksViewModel)
    }