package com.smokey.practicafct.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.smokey.practicafct.ContextApplication

@Database(entities = [InvoiceModelRoom::class, DetailsSmartSolarRoom::class], version = 1, exportSchema = false)
abstract class InvoiceDatabase: RoomDatabase() {
    abstract fun getInvoiceDao(): InvoiceDAO
    abstract fun getDetailsSmartSolarDAO(): DetailsSmartSolarDAO
    companion object{
        private var DB_INSTANCE: InvoiceDatabase? = null
        fun getAppDBInstance(): InvoiceDatabase{
            if (DB_INSTANCE == null){
                DB_INSTANCE = Room.databaseBuilder(ContextApplication.context, InvoiceDatabase::class.java,"invoice_database")
                    .allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE !!
        }
    }
}