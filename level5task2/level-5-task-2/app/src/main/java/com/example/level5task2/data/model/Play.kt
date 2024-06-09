package com.example.level5task2.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.level5task2.enums.PlayResult
import com.example.level5task2.enums.RockPaperScissors
import java.util.Date

@Entity(tableName = "play")
data class Play(
    @ColumnInfo(name = "date")
    var date: Date,

    @ColumnInfo(name = "result")
    var result: PlayResult,

    @ColumnInfo(name = "player")
    var playerMove: RockPaperScissors,

    @ColumnInfo(name = "computer")
    var computerMove: RockPaperScissors,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
)