package com.example.huerto_hogar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huerto_hogar.model.BlogPost
import com.example.huerto_hogar.model.BlogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BlogViewModel: ViewModel() {
    private val _blogPosts = MutableStateFlow<List<BlogPost>>(emptyList())
    val blogPosts: StateFlow<List<BlogPost>> = _blogPosts.asStateFlow()

    private val _selectedBlog = MutableStateFlow<BlogPost?>(null)
    val selectedBlog: StateFlow<BlogPost?> = _selectedBlog.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadBlogs()
    }


    fun loadBlogs(){
        _isLoading.value = true
        viewModelScope.launch {
            //simulamos la carga de datos
            try {
                _blogPosts.value = BlogRepository.getBlogs()
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun getBlogById(blogId: String){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _selectedBlog.value = BlogRepository.getBlogById(blogId)
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun clearSelectedBlog(){
        _selectedBlog.value = null
    }
}