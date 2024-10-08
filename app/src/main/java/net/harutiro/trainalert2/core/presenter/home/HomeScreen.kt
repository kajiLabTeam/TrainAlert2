package net.harutiro.trainalert2.core.presenter.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    toRouteEditor: () -> Unit,
    viewModel: HomeViewModel = viewModel()
){
    Column {
        Text(text = "HomeScreen")
        Button(
            onClick = {
                viewModel.test()
            }
        ) {
            Text(text = "Logのテスト")
        }
        Button(
            onClick = {
                toRouteEditor()
            }
        ) {
            Text(text = "経路作成画面へ")
        }
    }
}
