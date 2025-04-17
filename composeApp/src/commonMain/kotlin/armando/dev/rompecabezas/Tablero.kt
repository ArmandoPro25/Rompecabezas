package armando.dev.rompecabezas

import androidx.compose.runtime.Composable

expect class Tablero() {
    @Composable
    fun dibujarTablero()
}