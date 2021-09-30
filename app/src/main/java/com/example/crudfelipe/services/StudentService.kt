package com.example.crudfelipe.services
import android.util.Log
import com.example.crudfelipe.model.Student
import java.text.SimpleDateFormat

object StudentService {
    val listaEstudiantes: MutableList<Student> = mutableListOf()

    init {

        val sdf = SimpleDateFormat("dd/MM/yyyy")

        listaEstudiantes.add(
            Student(
                "1", sdf.parse("01/01/2000"), "Felipe", floatArrayOf(3.4F, 3.0F, 2.5F)
            )
        )
        listaEstudiantes.add(
            Student(
                "2", sdf.parse("01/01/2001"), "Diana", floatArrayOf(5.0F, 5.0F, 5.0F)
            )
        )
        listaEstudiantes.add(
            Student(
                "3", sdf.parse("01/01/2002"), "Andrea", floatArrayOf(4.4F, 4.0F, 3.5F)
            )
        )
        listaEstudiantes.add(
            Student(
                "4", sdf.parse("01/01/2003"), "Natalia", floatArrayOf(1.4F, 2.0F, 3.5F)
            )
        )
    }

    fun agregar(student: Student) {
        listaEstudiantes.add(0, student)
    }

    fun editar(student: Student) {
        val studentToEdit = listaEstudiantes.find { it.code.equals(student.code) }
        val index = listaEstudiantes.indexOf(studentToEdit)

        listaEstudiantes[index] = student
    }

    fun eliminar(codigo: String) {
        val student = listaEstudiantes.find { it.code.equals(codigo) }

        listaEstudiantes.remove(student)
    }

    fun obtener(codigo: String): Student? {
        return listaEstudiantes.find { it.code.equals(codigo) }
    }
}
