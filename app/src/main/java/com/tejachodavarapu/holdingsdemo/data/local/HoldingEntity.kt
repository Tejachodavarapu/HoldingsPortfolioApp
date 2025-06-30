package com.tejachodavarapu.holdingsdemo.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "holdings")
data class HoldingEntity(
    @PrimaryKey val symbol: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "ltp") val ltp: Float,
    @ColumnInfo(name = "avg_price") val avgPrice: Float,
    @ColumnInfo(name = "close") val close: Float
)