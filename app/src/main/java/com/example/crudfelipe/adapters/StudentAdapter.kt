package com.example.crudfelipe.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crudfelipe.DetailStudentActivity
import com.example.crudfelipe.model.Student
import com.example.crudfelipe.R

class StudentAdapter(val lista: MutableList<Student>, val context: Context) :
    RecyclerView.Adapter<StudentAdapter.ViewHolder>(), Filterable {

    var cpList: MutableList<Student> = lista

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindStudent(cpList[position])
    }

    override fun getItemCount(): Int {
        return cpList.size
    }

    inner class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        val name: TextView = itemView.findViewById(R.id.nameText)
        val code: TextView = itemView.findViewById(R.id.studentCode)
        val date: TextView = itemView.findViewById(R.id.dateText)

        fun bindStudent(student: Student) {
            name.text = student.name
            code.text = student.code
            date.text = student.date.toString()
        }

        override fun onClick(v: View?) {
            val intent = Intent(context, DetailStudentActivity::class.java)
            intent.putExtra("item_estudiante", cpList[adapterPosition].code)
            context.startActivity(intent)
        }
    }

    fun filtrar(s: String?) {
        filter.filter(s)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val characters = constraint.toString()
                if (characters.isEmpty()) {
                    cpList = lista
                } else {
                    cpList = mutableListOf()
                    val resultados = lista.filter { estudiante ->
                        estudiante.name!!.lowercase().contains(characters.lowercase())
                    }
                    cpList.addAll(resultados)
                }
                return filterResults.also {
                    it.values = cpList
                }
            }

            override fun publishResults(
                constraint: CharSequence?, results:
                FilterResults?
            ) {
                cpList = results?.values as MutableList<Student>
                notifyDataSetChanged()
            }
        }
    }
}