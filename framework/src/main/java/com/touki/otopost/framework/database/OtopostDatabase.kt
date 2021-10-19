package com.touki.otopost.framework.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.touki.otopost.framework.core.post.model.PostEntity
import com.touki.otopost.framework.core.post.source.PostDao

@Database(
    entities = [PostEntity::class],
    version = 1,
    exportSchema = false
)
abstract class OtopostDatabase: RoomDatabase() {

    abstract fun getPostDao(): PostDao

    companion object{

        private const val NAME = "AirisRoomDatabase"

        private var roomDatabase: OtopostDatabase? = null

        /**
         * Example: when you have tickets table in version 1, then you add new column into it in version 2
         * add this code:
         *
         * private val MIGRATION_1_2 = object: Migration(1, 2){
         *      override fun migrate(database: SupportSQLiteDatabase) {
         *          database.execSQL("ALTER TABLE tickets ADD COLUMN trf_cat TEXT")
         *      }
         * }
         *
         * then add it to migrations
         *
         * */

        fun getInstance(context: Context): OtopostDatabase? {
            if (roomDatabase == null){
                roomDatabase = Room.databaseBuilder(context, OtopostDatabase::class.java, NAME)
                    .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                    .addMigrations()
                    .build()
            }
            return roomDatabase
        }
    }
}