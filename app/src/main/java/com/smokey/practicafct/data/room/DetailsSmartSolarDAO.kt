package com.smokey.practicafct.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DetailsSmartSolarDAO {
    @Query("SELECT * FROM detailsSmartSolar_table")
    fun getDetailsFromRoom() : DetailsSmartSolarRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailsInRoom(DetailsSmartSolarRoom: DetailsSmartSolarRoom )

    @Query("DELETE FROM detailsSmartSolar_table")
    fun deleteEnergyDataFromRoom()
}