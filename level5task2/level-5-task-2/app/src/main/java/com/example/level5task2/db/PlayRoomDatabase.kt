package com.example.level5task2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.level5task2.data.model.Play

@Database(entities = [Play::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PlayRoomDatabase : RoomDatabase() {

    abstract fun playDao(): PlayDao

    companion object {
        private const val DATABASE_NAME = "GAME_DATABASE"

        @Volatile
        private var INSTANCE: PlayRoomDatabase? = null

        fun getDatabase(context: Context): PlayRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(PlayRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            PlayRoomDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
