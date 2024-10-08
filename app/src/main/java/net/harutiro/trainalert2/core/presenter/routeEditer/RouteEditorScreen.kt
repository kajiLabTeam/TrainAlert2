package net.harutiro.trainalert2.core.presenter.routeEditer

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RouteEditScreen(
    toBackScreen: () -> Unit,
    viewModel: RouteEditorViewModel = viewModel()
){
    Column {
        Text(text = "RouteEditScreen")

        Button(
            onClick = {
                viewModel.test()
            }
        ) {
            Text(text = "Logのテスト")
        }

        Button(
            onClick = {
                toBackScreen()
            }
        ) {
            Text(text = "戻る")
        }
    }
}