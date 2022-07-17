package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.room.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        binding.btnReadData.setOnClickListener { readData() }
        binding.btnWriteData.setOnClickListener { writeData() }
    }

    private fun writeData() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val rollNumber = binding.etRollNumberWrite.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && rollNumber.isNotEmpty()) {
            val student = Student(null, firstName, lastName, rollNumber.toInt())

            GlobalScope.launch(Dispatchers.IO) {
                database.studentDao().insert(student)
            }

            binding.etFirstName.text.clear()
            binding.etLastName.text.clear()
            binding.etRollNumberWrite.text.clear()

            Toast.makeText(this, R.string.student_added, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.db_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun readData() {
        val rollNumber = binding.etRollNumberRead.text.toString()

        if (rollNumber.isNotEmpty()) {
            lateinit var student: Student

            GlobalScope.launch {
                student = database.studentDao().findByRoll(rollNumber.toInt())
                displayStudent(student)
            }
        }

    }

    private suspend fun displayStudent(student: Student) {

        withContext(Dispatchers.Main) {
            binding.tvLoadFirstName.text = student.firstName
            binding.tvLoadLastName.text = student.lastName
            binding.tvLoadRollNo.text = student.rollNumber.toString()
        }
    }
}