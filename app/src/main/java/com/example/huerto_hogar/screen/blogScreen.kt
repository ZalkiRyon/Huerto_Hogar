package com.example.huerto_hogar.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.example.huerto_hogar.AppScreens.AppScreens
import com.example.huerto_hogar.R
import com.example.huerto_hogar.ui.theme.components.Header
import org.jetbrains.annotations.Async

data class BlogPost(
    val id: String,
    val titulo: String,
    val contenido: String,
    val autor: String,
    val fecha: String,
    val categoria: String,
    val imagenUrl: String? = null
)

object BlogRepository {
    fun getBlogs(): List<BlogPost>{
        return listOf(
            BlogPost(
                id = "1",
                titulo = "Introducción",
                contenido = "Jetpack Compose...",
                autor = "Perico Palore",
                fecha = "15 - marzo 2020",
                categoria = "Verduras",
                imagenUrl = null
            ),
            BlogPost(
                id = "2",
                titulo = "Perico Palote",
                contenido = "Perico pelote",
                autor = "Pepa la cerda",
                fecha = "11 - marzo - 2020",
                categoria = "Frutas",
                null
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun tarjetaBlog(
    blogPost: BlogPost,
    onCardClick: (BlogPost) -> Unit,
){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = {onCardClick(blogPost)},
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ){
        Column {
            blogPost.imagenUrl?.let { imageUrl ->
                Image(
                    painter = painterResource(id = R.drawable.kale),
                    contentDescription = "Blog Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            //Teorico, contenido
            Column (
                modifier = Modifier.padding(16.dp)
            ){
                //Categoría
                Text(
                    text = blogPost.categoria.uppercase(),
                    syle = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )

                //Titulo
                Text(
                    text = blogPost.titulo,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )

                //Contenido Preview
                Text(
                    text = blogPost.contenido.take(100) + "...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(bottom = 12.dp),
                    maxLines = 2
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                ){
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = CircleShape
                            )
                    ){
                        Text(
                            text = blogPost.autor.first().toString(),
                            style
                        )
                    }
                }
            }
        }
    }
}