package com.example.crudfelipe

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.crudfelipe.model.Student
import com.example.crudfelipe.services.StudentService
import com.example.crudfelipe.databinding.ActivityAddStudentBinding
import java.text.SimpleDateFormat

class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun saveStudent(v: View) {
        val sdf = SimpleDateFormat("dd/MM/yyyy")

        val code = binding.codigoEstudiante.text.toString()
        val name = binding.nombreEstudiante.text.toString()
        val date = sdf.parse(binding.date.text.toString())!!
        val gradeOne = binding.firstGrade.text.toString()
        val gradeTwo = binding.gradeTwo.text.toString()
        val notaThree = binding.gradeThree.text.toString()


        val grades = floatArrayOf(gradeOne.toFloat(), gradeTwo.toFloat(), notaThree.toFloat())
        val estudiante =
            Student(code, date,name, grades)

        StudentService.agregar(estudiante)
        val intent = intent
        setResult(1, intent)

        finish()
    }
}