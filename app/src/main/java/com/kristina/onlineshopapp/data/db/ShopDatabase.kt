package com.kristina.onlineshopapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kristina.onlineshopapp.data.db.dao.ProductDao
import com.kristina.onlineshopapp.data.db.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ShopDatabase : RoomDatabase() {

    abstract val productDao: ProductDao

    companion object {

        @Volatile
        private var INSTANCE: ShopDatabase?= null

        fun getInstance(context: Context): ShopDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ShopDatabase::class.java,
                        "shop_database"
                    ).build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}