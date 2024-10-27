package com.example.ecommerceapp.di

import com.example.ecommerceapp.data.api.repo.ApiRepository
import com.example.ecommerceapp.data.api.repo.ApiRepositoryImpl
import com.example.ecommerceapp.data.cache.repo.CartItemRepository
import com.example.ecommerceapp.data.cache.repo.CartItemRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindApiRepository(apiRepositoryImpl: ApiRepositoryImpl): ApiRepository

    @Binds
    abstract fun bindCartItemRepository(cartItemRepositoryImpl: CartItemRepositoryImpl): CartItemRepository
}