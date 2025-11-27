package org.christophertwo.eikocolors.di

import org.christophertwo.eikocolors.domain.usecase.SyncClientsUseCase
import org.christophertwo.eikocolors.domain.usecase.SyncWorksUseCase
import org.christophertwo.eikocolors.feature.clients.domain.*
import org.christophertwo.eikocolors.feature.works.domain.DeleteWorkUseCase
import org.christophertwo.eikocolors.feature.works.domain.GetAllWorksUseCase
import org.christophertwo.eikocolors.feature.works.domain.GetOverdueWorksUseCase
import org.christophertwo.eikocolors.feature.works.domain.SaveWorkUseCase
import org.christophertwo.eikocolors.feature.works.domain.UpdateWorkUseCase
import org.koin.dsl.module

val useCaseModule
    get() = module {
        // Sync Use Cases
        single { SyncClientsUseCase(get()) }
        single { SyncWorksUseCase(get()) }

        // Works Use Cases
        single { GetAllWorksUseCase(get()) }
        single { GetOverdueWorksUseCase(get()) }
        single { SaveWorkUseCase(get()) }
        single { UpdateWorkUseCase(get()) }
        single { DeleteWorkUseCase(get()) }

        // Clients Use Cases
        single { GetAllClientsUseCase(get()) }
        single { SaveClientUseCase(get()) }
        single { UpdateClientUseCase(get()) }
        single { DeleteClientUseCase(get()) }
        single { SearchClientsUseCase(get()) }
    }