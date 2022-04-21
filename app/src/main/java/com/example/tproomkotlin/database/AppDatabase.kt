package com.example.tproomkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [PersonneEntity::class], version = 1)
@TypeConverters(
    DateConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personneDao(): PersonneDao?

    companion object {
        const val DATABASE_NAME = "AppDatabase.db"

        // ce variable est une instance de base de donnée qui est partagé
        // par plusieurs thread, elle doit être toujours en mémoire, jamais
        // en cache
        @Volatile
        private var instance: AppDatabase? = null

        // l'acces à l'instance doit être synchronisé
        private val LOCK = Any()
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            DATABASE_NAME
                        ) //                            .allowMainThreadQueries()
                            //                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return instance
        }
    }
}