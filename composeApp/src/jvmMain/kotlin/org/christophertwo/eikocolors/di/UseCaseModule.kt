package org.christophertwo.eikocolors.di

import org.christophertwo.eikocolors.feature.works.domain.DeleteWorkUseCase
import org.christophertwo.eikocolors.feature.works.domain.GetAllWorksUseCase
import org.christophertwo.eikocolors.feature.works.domain.GetOverdueWorksUseCase
import org.christophertwo.eikocolors.feature.works.domain.SaveWorkUseCase
import org.christophertwo.eikocolors.feature.works.domain.UpdateWorkUseCase
import org.koin.dsl.module

val useCaseModule
    get() = module {
        single { GetAllWorksUseCase(get()) }
        single { GetOverdueWorksUseCase(get()) }
        single { SaveWorkUseCase(get()) }
        single { UpdateWorkUseCase(get()) }
        single { DeleteWorkUseCase(get()) }
        single {  }
    }