package com.tejachodavarapu.holdingsdemo.di

import android.content.Context
import androidx.room.Room
import com.tejachodavarapu.holdingsdemo.data.local.HoldingsDao
import com.tejachodavarapu.holdingsdemo.data.local.HoldingsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideHoldingsDatabase(
        @ApplicationContext appContext: Context
    ): HoldingsDatabase {
        return Room.databaseBuilder(
            appContext,
            HoldingsDatabase::class.java,
            "holdings_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHoldingsDao(db: HoldingsDatabase): HoldingsDao {
        return db.holdingsDao()
    }
}