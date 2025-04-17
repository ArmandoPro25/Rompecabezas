package armando.dev.rompecabezas

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

actual class Tablero {
    @Composable
    actual fun dibujarTablero() {
        var selectedImage by remember { mutableStateOf(1) }
        val logicPuzzle = remember(selectedImage) { LogicPuzzle(selectedImage) }
        val currentImages = logicPuzzle.piezas.value.map { painterResource(id = it) }

        var mostrarDialogo by remember { mutableStateOf(false) }

        LaunchedEffect(logicPuzzle.piezas.value) {
            if (logicPuzzle.yaGano()) {
                mostrarDialogo = true
            }
        }

        if (mostrarDialogo) {
            VictoriaDialogo {
                mostrarDialogo = false
                logicPuzzle.mezclarPiezas()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF6DD5FA), Color(0xFF2193B0))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Puzzle Mágico",
                    style = MaterialTheme.typography.h4,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { selectedImage = if (selectedImage == 1) 2 else 1 },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF4CAF50),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cambiar Imagen", fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { logicPuzzle.mezclarPiezas() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFE91E63),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Mezclar Piezas", fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(currentImages.size) { index ->
                        ImageTile(
                            image = currentImages[index],
                            index = index,
                            onClick = { logicPuzzle.seleccionarPieza(index) }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun VictoriaDialogo(onDismiss: () -> Unit) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color(0xFFE8F5E9),
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_celebration),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Color(0xFF4CAF50)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "¡Felicidades!",
                        style = MaterialTheme.typography.h5,
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Has completado el puzzle",
                        style = MaterialTheme.typography.body1,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF4CAF50),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Continuar", fontSize = 16.sp)
                    }
                }
            }
        }
    }

    @Composable
    fun ImageTile(image: Painter, index: Int, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .padding(4.dp)
                .aspectRatio(1f)
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        dibujarTablero()
    }
}