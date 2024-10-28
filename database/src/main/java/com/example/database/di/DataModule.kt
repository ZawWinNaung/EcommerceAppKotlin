package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.data.CartItemDao
import com.example.database.data.EcommerceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): EcommerceDatabase {
        return Room.databaseBuilder(
            appContext,
            EcommerceDatabase::class.java,
            "ecommerce_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(myEventDatabase: EcommerceDatabase): CartItemDao {
        return myEventDatabase.myEventDao()
    }
}