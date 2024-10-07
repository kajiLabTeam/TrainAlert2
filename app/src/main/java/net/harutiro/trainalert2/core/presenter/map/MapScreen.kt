package net.harutiro.trainalert2.core.presenter.map

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MapScreen(
    viewModel: MapViewModel = viewModel()
){
    Column {
        Text(text = "MapScreen")
        Button(
            onClick = {
                viewModel.test()
            }
        ) {
            Text(text = "Logのテスト")
        }
    }
}
