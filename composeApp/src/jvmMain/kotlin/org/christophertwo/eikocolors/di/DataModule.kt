package org.christophertwo.eikocolors.di

import org.christophertwo.eikocolors.feature.works.data.WorkRepositoryImpl
import org.christophertwo.eikocolors.feature.works.desingsystem.WorkRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule
    get() = module {
        singleOf(::WorkRepositoryImpl).bind(WorkRepository::class)
    }