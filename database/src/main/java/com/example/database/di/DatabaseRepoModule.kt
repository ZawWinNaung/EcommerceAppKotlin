package com.example.database.di

import com.example.database.data.repo.CartItemRepository
import com.example.database.data.repo.CartItemRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DatabaseRepoModule {

    @Binds
    abstract fun bindCartItemRepository(cartItemRepositoryImpl: CartItemRepositoryImpl): CartItemRepository
}