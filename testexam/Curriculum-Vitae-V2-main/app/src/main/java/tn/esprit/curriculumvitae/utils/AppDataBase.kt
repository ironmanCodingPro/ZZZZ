package tn.esprit.curriculumvitae.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tn.esprit.curriculumvitae.dao.PersonneDao
import tn.esprit.curriculumvitae.dao.ExperienceDao
import tn.esprit.curriculumvitae.data.Experience
import tn.esprit.curriculumvitae.data.Personne

@Database(entities = [Personne::class, Experience::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun experienceDao(): ExperienceDao
    abstract fun educationDao(): PersonneDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            if (instance == null) {
                synchronized(this) {
                    instance =
                        Room.databaseBuilder(context, AppDataBase::class.java, "DATABASE")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return instance!!
        }
    }
}


