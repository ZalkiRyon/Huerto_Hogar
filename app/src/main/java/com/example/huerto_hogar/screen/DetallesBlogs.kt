package com.example.huerto_hogar.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.huerto_hogar.model.BlogPost
import com.example.huerto_hogar.viewModel.BlogViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesBlogsScreen(
    navController: NavHostController,
    blogId: String,
    blogViewModel: BlogViewModel
){
    val selectedBlog by blogViewModel.selectedBlog.collectAsState()
    val isLoading by blogViewModel.isLoading.collectAsState()
    var isLiked by remember {mutableStateOf(false)}

    //Cargar el blog cuando se abre la pantalla
    LaunchedEffect(blogId) {
        blogViewModel.getBlogById(blogId)
    }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Text(
                        "Artículo",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver al blog"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {isLiked = !isLiked}) {
                        Icon(
                            imageVector = if(isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if(isLiked) "Quitar de favoritos" else "Agregar a favoritos",
                            tint = if(isLiked) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    IconButton(onClick = {}){
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Compartir Artículo"
                        )
                    }
                }
            )
        }
    ){innerPadding ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ){
                    Text("Cargando Artículo...")
                }
            }
            selectedBlog == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ){
                    Text("Artículo no encontrado")
                }
            }
            else -> {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ){
                    //Imagen del blog
                    selectedBlog?.imagenUrl?.let {imageUrl ->
                        Image(
                            painter = painterResource(id = imageUrl),
                            contentDescription = selectedBlog!!.titulo,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    //Contenido del blog
                    Column (
                        modifier = Modifier
                            .padding(16.dp)
                    ){
                        //Categoria
                        Text(
                            text = selectedBlog!!.categoria.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        //Titulo
                        Text(
                            text = selectedBlog!!.titulo,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                        )

                        //Información del autor y fecha
                        InfoAutor(selectedBlog!!)

                        Spacer(modifier = Modifier.height(24.dp))

                        //Contenido
                        Text(
                            text = selectedBlog!!.contenido,
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight *1.3,
                            textAlign = TextAlign.Justify
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun InfoAutor(blog: BlogPost){
    Card (
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row (
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            //Avatar
            Box(modifier = Modifier
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = blog.autor.first().toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.size(12.dp))

            Column {
                Text(
                    text = blog.autor,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = blog.fecha,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}