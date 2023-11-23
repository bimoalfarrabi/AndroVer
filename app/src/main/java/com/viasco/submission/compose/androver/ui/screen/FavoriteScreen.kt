package com.viasco.submission.compose.androver.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.viasco.submission.compose.androver.R
import com.viasco.submission.compose.androver.di.Injection
import com.viasco.submission.compose.androver.model.AndroVer
import com.viasco.submission.compose.androver.ui.common.UiState
import com.viasco.submission.compose.androver.ui.item.EmptyList
import com.viasco.submission.compose.androver.ui.viewmodel.FavoriteViewModel
import com.viasco.submission.compose.androver.ui.viewmodel.ViewModelFactory

@Composable
fun FavoriteScreen(
    navigateToDetail: (Int) -> Unit,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteAndroVer()
            }
            is UiState.Success -> {
                FavoriteInfo(
                    listAndroVer = uiState.data,
                    navigateToDetail = navigateToDetail,
                    onFavoriteIconClick = { id, newState ->
                        viewModel.updatePlayer(id, newState)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteInfo(
    listAndroVer: List<AndroVer>,
    navigateToDetail: (Int) -> Unit,
    onFavoriteIconClick: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        if (listAndroVer.isNotEmpty()) {
            ListAndroVer(
                listAndroVer = listAndroVer,
                onFavoriteIconClick = onFavoriteIconClick,
                contentPaddingTop = 16.dp,
                navigateToDetail = navigateToDetail
            )
        } else {
            EmptyList(
                warning = stringResource(R.string.empty_favorite)
            )
        }
    }
}