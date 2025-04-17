package armando.dev.rompecabezas

import androidx.compose.runtime.mutableStateOf

class LogicPuzzle(imagen: Int) {

    private val piezasOriginales = when(imagen) {
        1 -> listOf(
            R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4,
            R.drawable.b5, R.drawable.b6, R.drawable.b7, R.drawable.b8,
            R.drawable.b9, R.drawable.b10, R.drawable.b11, R.drawable.b12,
            R.drawable.b13, R.drawable.b14, R.drawable.b15, R.drawable.b16
        )
        2 -> listOf(
            R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4,
            R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8,
            R.drawable.a9, R.drawable.a10, R.drawable.a11, R.drawable.a12,
            R.drawable.a13, R.drawable.a14, R.drawable.a15, R.drawable.a16
        )
        else -> throw IllegalArgumentException("Imagen no válida")
    }

    val piezas = mutableStateOf(piezasOriginales.toList())
    private val seleccionada = mutableStateOf<Int?>(null)
    private val haSidoMezclado = mutableStateOf(false)

    fun mezclarPiezas() {
        val listaMezclada = piezas.value.toMutableList()
        listaMezclada.shuffle()
        piezas.value = listaMezclada
        seleccionada.value = null
        haSidoMezclado.value = true
    }

    fun seleccionarPieza(indice: Int) {
        val indexSeleccionado = seleccionada.value
        if (indexSeleccionado == null) {
            seleccionada.value = indice
        } else {
            if(indexSeleccionado != indice) {
                intercambiarPiezas(indexSeleccionado, indice)
                seleccionada.value = null
            }
        }
    }

    fun intercambiarPiezas(indice1: Int, indice2: Int) {
        val listaActual = piezas.value.toMutableList()
        val temp = listaActual[indice1]
        listaActual[indice1] = listaActual[indice2]
        listaActual[indice2] = temp
        piezas.value = listaActual
    }

    fun yaGano():Boolean {
        return haSidoMezclado.value && piezas.value == piezasOriginales
    }
}