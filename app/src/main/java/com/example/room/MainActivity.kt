package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.room.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                database.studentDao().addStudent(student)
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

    }
}