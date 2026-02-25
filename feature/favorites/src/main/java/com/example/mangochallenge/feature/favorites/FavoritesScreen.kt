package com.example.mangochallenge.feature.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mangochallenge.core.ui.EmptyState
import com.example.mangochallenge.core.ui.ProductListContent

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()

    if (favorites.isEmpty()) {
        EmptyState(message = stringResource(R.string.favorites_empty))
    } else {
        ProductListContent(
            products = favorites,
            onFavoriteClick = viewModel::toggleFavorite
        )
    }
}
