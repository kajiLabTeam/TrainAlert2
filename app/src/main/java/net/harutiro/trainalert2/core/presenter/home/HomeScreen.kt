package net.harutiro.trainalert2.core.presenter.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun HomeScreen(
    toRouteEditor: () -> Unit,
    viewModel: HomeViewModel = viewModel()
){
    val context = LocalContext.current

    Column {
        Text(text = "HomeScreen")
        Button(
            onClick = {
                // テスト通知とバイブレーションを送る
                viewModel.test(context)
            }
        ) {
            Text(text = "通知＆バイブレーションテスト")
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
