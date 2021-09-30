package com.example.crudfelipe

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.crudfelipe.databinding.ActivityEditStudentBinding
import com.example.crudfelipe.model.Student
import java.text.SimpleDateFormat
import com.example.crudfelipe.services.StudentService

class EditStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditStudentBinding
    var position = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val student = intent.getParcelableExtra<Student>("student")
        position = intent.getIntExtra("position", -1)

        if (student != null && position != -1) {
            binding.studentCode.isEnabled = false
            getData(student)
        }
    }
    fun getData(student: Student) {
        val sdf = SimpleDateFormat("dd/MM/yyyy")

        binding.studentCode.setText(student.code)
        binding.studentName.setText(student.name)
        val fechaNac = sdf.format(student.date)
        binding.date.setText(fechaNac)
        binding.firstGrade.setText("${student.grades?.get(0)}")
        binding.gradeTwo.setText("${student.grades?.get(1)}")
        binding.gradeThree.setText("${student.grades?.get(2)}")
    }

    fun updateStudent(v: View) {
        val sdf = SimpleDateFormat("dd/MM/yyyy")

        val code = binding.studentCode.text.toString()
        val name = binding.studentName.text.toString()
        val date = sdf.parse(binding.date.text.toString())!!
        val gradeOne = binding.firstGrade.text.toString()
        val gradeTwo = binding.gradeTwo.text.toString()
        val notaThree = binding.gradeThree.text.toString()

        val grades = floatArrayOf(gradeOne.toFloat(), gradeTwo.toFloat(), notaThree.toFloat())
        val estudiante =
            Student(code, date,name, grades)

        StudentService.editar(estudiante)
        val intent = intent
        intent.putExtra("position", position)
        setResult(2, intent)

        finish()
    }

}