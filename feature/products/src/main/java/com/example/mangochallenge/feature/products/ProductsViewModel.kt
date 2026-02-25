package com.example.mangochallenge.feature.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangochallenge.core.domain.model.Product
import com.example.mangochallenge.core.domain.usecase.GetProductsUseCase
import com.example.mangochallenge.core.domain.usecase.ToggleFavoriteUseCase
import com.example.mangochallenge.core.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Product>>> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var allProducts: List<Product> = emptyList()

    init {
        loadProducts()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        applyFilter()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                allProducts = getProductsUseCase()
                applyFilter()
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun refreshProducts() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                allProducts = getProductsUseCase()
                applyFilter()
            } catch (_: Exception) {
                // Keep current list visible on refresh failure
            }
            _isRefreshing.value = false
        }
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(product)
                allProducts = getProductsUseCase()
                applyFilter()
            } catch (_: Exception) {
                // Silently handle toggle errors
            }
        }
    }

    private fun applyFilter() {
        val query = _searchQuery.value.trim()
        val filtered = if (query.isEmpty()) {
            allProducts
        } else {
            allProducts.filter { product ->
                product.title.contains(query, ignoreCase = true) ||
                    product.category.contains(query, ignoreCase = true)
            }
        }
        _uiState.value = UiState.Success(filtered)
    }
}
