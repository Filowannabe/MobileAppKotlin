package com.example.crudfelipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.crudfelipe.services.StudentService
import com.example.crudfelipe.databinding.ActivityDetailStudentBinding


class DetailStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val code = intent.getStringExtra("item_estudiante")

        if (code != null) {
            binding.infoEstudiante.text = StudentService.obtener(code).toString()
        }
    }


}