package com.example.huerto_hogar.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.huerto_hogar.R
import com.example.huerto_hogar.ui.theme.components.CatalogoNavigation

data class ProductoVerduras(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val urlImagen: Int = R.drawable.imagen_no_found //defecto

)

//                      Productos de ejemplo
val productosVerduras = listOf(
    ProductoVerduras(1, "Manzanas Fuji",1200, R.drawable.manzana_fuji),
    ProductoVerduras(2, "Naranjas Valencia", 1000, R.drawable.naranja_valencia),
    ProductoVerduras(3, "Platanos Cavendish", 800, R.drawable.platano)
)

@Composable
fun VerdurasScreen (navController: NavHostController){
    Scaffold(
        topBar = {
            CatalogoNavigation(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Catálogo de Vegetales", style = MaterialTheme.typography.headlineMedium)
            // Aquí tu contenido específico de vegetales
        }
    }
}