package com.example.level5task2.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.level5task2.data.model.Play
import com.example.level5task2.enums.PlayResult

@Dao
interface PlayDao {

    @Query("SELECT * from play")
    fun getGames(): LiveData<List<Play>>

    @Insert
    suspend fun insert(game: Play)

//    @Insert
//    suspend fun insert(game: List<Play>)

//    @Delete
//    suspend fun delete(game: Play)

    @Query("DELETE from play")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM play WHERE result = :result")
    fun getGameResult(result: PlayResult): LiveData<Int>

    fun getTotalWins(): LiveData<Int> {
        return getGameResult(PlayResult.WIN)
    }

    fun getTotalLosses(): LiveData<Int> {
        return getGameResult(PlayResult.LOSE)
    }

    fun getTotalDraws(): LiveData<Int> {
        return getGameResult(PlayResult.DRAW)
    }
}