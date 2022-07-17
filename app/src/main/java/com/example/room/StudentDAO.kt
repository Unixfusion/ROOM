package com.example.room

import androidx.room.*

@Dao
interface StudentDAO {

    @Query("SELECT * FROM `student_table`")
    fun getAll(): List<Student>

    @Query("SELECT * FROM `student_table` WHERE rollNumber LIKE: roll LIMIT 1")
    suspend fun findByRoll(roll: Int): Student

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStudent(student: Student)

    @Delete
    suspend fun deleteStduent(student: Student)
    @Query("DELETE FROM student_table")
    suspend fun deleteAll()
}