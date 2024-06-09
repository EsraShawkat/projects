package com.example.level5task2.repository

import android.content.Context
import com.example.level5task2.data.model.Play
import com.example.level5task2.db.PlayDao
import com.example.level5task2.db.PlayRoomDatabase

class PlayRepository(context: Context) {

    private val playDao: PlayDao

    init {
        val database = PlayRoomDatabase.getDatabase(context)
        playDao = database!!.playDao()
    }

    suspend fun insert(play: Play) = playDao.insert(play)

//    suspend fun delete(game: Game) = gameDao.delete(game)

    fun getGames() = playDao.getGames()

    suspend fun deleteAll() = playDao.deleteAll()

    fun getTotalWins() = playDao.getTotalWins()

    fun getTotalLosses() = playDao.getTotalLosses()

    fun getTotalDraws() = playDao.getTotalDraws()

}