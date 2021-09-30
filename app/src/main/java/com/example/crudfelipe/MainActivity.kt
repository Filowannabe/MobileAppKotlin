package com.example.crudfelipe

//███████╗███████╗██╗     ██╗██████╗ ███████╗
//██╔════╝██╔════╝██║     ██║██╔══██╗██╔════╝
//█████╗  █████╗  ██║     ██║██████╔╝█████╗
//██╔══╝  ██╔══╝  ██║     ██║██╔═══╝ ██╔══╝
//██║     ███████╗███████╗██║██║     ███████╗
//╚═╝     ╚══════╝╚══════╝╚═╝╚═╝     ╚══════╝

// CRUD APP MOVILES -> Felipe Corredor Castro

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.example.crudfelipe.model.Student
import com.example.crudfelipe.services.StudentService
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.crudfelipe.adapters.StudentAdapter
import com.example.crudfelipe.databinding.ActivityMainBinding
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listaEstudiantes: MutableList<Student>
    private lateinit var adapter: StudentAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                onActivityResult(it.resultCode, it)
            }

        listaEstudiantes = StudentService.listaEstudiantes

        adapter = StudentAdapter(listaEstudiantes, this)

        binding.studentList.adapter = adapter
        binding.studentList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val simpleCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val i = viewHolder.adapterPosition
                val student = listaEstudiantes[i]

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        StudentService.eliminar(student.code!!)
                        adapter.notifyItemRemoved(i)

                        Snackbar.make(
                            binding.studentList,
                            "Se ha eliminado exitosamente",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Deshacer", View.OnClickListener { it ->
                                StudentService.listaEstudiantes.add(i, student)
                                adapter.notifyItemInserted(i)
                            }).show()
                    }
                    ItemTouchHelper.RIGHT -> {
                        editarEstudiante(student, i)
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftLabel("Eliminar")
                    .addSwipeRightLabel("Editar")
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(baseContext, R.color.purple_200))
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(baseContext, R.color.purple_200))
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.studentList)

    }

    fun agregarEstudiante() {
        val intent = Intent(this, AddStudentActivity::class.java)
        resultLauncher.launch(intent)
    }

    fun editarEstudiante(student: Student, i: Int) {
        val intent = Intent(this, EditStudentActivity::class.java)
        intent.putExtra("student", student)
        intent.putExtra("position", i)
        resultLauncher.launch(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.student_menu, menu)

        val item = menu?.findItem(R.id.menu_buscar)

        val searchView: SearchView = item!!.actionView as SearchView

        searchView.queryHint = "Nombre del estudiante"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.filtrar(newText)
                }

                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_agregar -> {
                agregarEstudiante()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onActivityResult(resultCode: Int, result: ActivityResult) {
        val data = result.data?.extras

        when (resultCode) {
            1 -> {
                adapter.notifyItemInserted(0)
            }
            2 -> {
                val i = data!!.getInt("position")

                adapter.notifyItemChanged(i)
            }
            else -> {
                adapter.notifyDataSetChanged()
            }
        }
    }
}