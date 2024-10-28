package com.example.ecommerceapp.di

import com.example.ecommerceapp.data.repo.ApiRepository
import com.example.ecommerceapp.data.repo.ApiRepositoryImpl
import com.example.database.data.repo.CartItemRepository
import com.example.database.data.repo.CartItemRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindApiRepository(apiRepositoryImpl: ApiRepositoryImpl): ApiRepository
}