package tn.esprit.curriculumvitae.dao

import androidx.room.*
import tn.esprit.curriculumvitae.data.Personne

@Dao
interface PersonneDao {
    @Insert
    fun insert(edc: Personne)

    @Update
    fun update(edc: Personne)

    @Delete
    fun delete(edc: Personne)

    @Query("SELECT * FROM education")
    fun getAllEducations(): MutableList<Personne>
}