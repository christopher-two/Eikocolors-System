package org.christophertwo.eikocolors.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.christophertwo.eikocolors.data.datasource.local.LocalDataSource
import org.christophertwo.eikocolors.data.datasource.local.LocalDataSourceImpl
import org.christophertwo.eikocolors.data.datasource.remote.RemoteDataSource
import org.christophertwo.eikocolors.data.datasource.remote.RemoteDataSourceImpl
import org.christophertwo.eikocolors.data.local.AppDatabase
import org.christophertwo.eikocolors.data.repository.AppRepositoryImpl
import org.christophertwo.eikocolors.domain.repository.AppRepository
import org.koin.dsl.module
import java.io.File

val dataModule
    get() = module {
        // Room Database
        single {
            val dbFile = File(System.getProperty("user.home"), "eikocolors_database.db")
            Room.databaseBuilder<AppDatabase>(
                name = dbFile.absolutePath,
            )
                .setDriver(BundledSQLiteDriver())
                .build()
        }

        // DAOs
        single { get<AppDatabase>().clientDao() }
        single { get<AppDatabase>().workDao() }

        // Data Sources
        single<LocalDataSource> { LocalDataSourceImpl(get(), get()) }
        single<RemoteDataSource> { RemoteDataSourceImpl() }

        // Repository
        single<AppRepository> { AppRepositoryImpl(get(), get()) }
    }