package com.example.crudfelipe.utils

import android.content.Context
import android.widget.Toast

object Constantes {
    fun mostrarMensaje(context: Context, mensaje:String) {
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
    }
}