package net.harutiro.trainalert2.core.presenter.routeEditer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RouteEditScreen(
    toBackScreen: () -> Unit,
    viewModel: RouteEditorViewModel = viewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "RouteEditScreen")

        Button(
            onClick = {
                viewModel.test()
            }
        ) {
            Text(text = "Logのテスト")
        }

        Text(text = "入力フォーム")

        // ルート名入力フィールド
        TextField(
            value = viewModel.title,
            onValueChange = { viewModel.title = it },
            label = { Text("ルート名") }
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 出発地点の経度入力フィールド
        TextField(
            value = viewModel.startLongitude,
            onValueChange = { viewModel.startLongitude = it },
            label = { Text("出発地点の経度") }
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 出発地点の緯度入力フィールド
        TextField(
            value = viewModel.startLatitude,
            onValueChange = { viewModel.startLatitude = it },
            label = { Text("出発地点の緯度") }
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 到着地点の経度入力フィールド
        TextField(
            value = viewModel.endLongitude,
            onValueChange = { viewModel.endLongitude = it },
            label = { Text("到着地点の経度") }
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 到着地点の緯度入力フィールド
        TextField(
            value = viewModel.endLatitude,
            onValueChange = { viewModel.endLatitude = it },
            label = { Text("到着地点の緯度") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = { /*TODO*/ }) {
            Text(text = "保存")
            //保存ボタンは一旦ボタンがあるだけです。後から保存機能を追加します。
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
