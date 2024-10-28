package com.example.database.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.domain.entity.CartItemEntity

@Database(entities = [CartItemEntity::class], version = 1)
abstract class EcommerceDatabase : RoomDatabase() {

    abstract fun myEventDao(): CartItemDao

}