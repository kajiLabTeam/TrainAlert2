package net.harutiro.trainalert2.core.presenter.routeEditer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
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
            label = { Text("出発地点の経度") },
            placeholder = { Text("例: 123.456") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 出発地点の緯度入力フィールド
        TextField(
            value = viewModel.startLatitude,
            onValueChange = { viewModel.startLatitude = it },
            label = { Text("出発地点の緯度") },
            placeholder = { Text("例: 34.567") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)

        )
        Spacer(modifier = Modifier.height(10.dp))

        // 到着地点の経度入力フィールド
        TextField(
            value = viewModel.endLongitude,
            onValueChange = { viewModel.endLongitude = it },
            label = { Text("到着地点の経度") },
            placeholder = { Text("例: 123.456") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 到着地点の緯度入力フィールド
        TextField(
            value = viewModel.endLatitude,
            onValueChange = { viewModel.endLatitude = it },
            label = { Text("到着地点の緯度") },
            placeholder = { Text("例: 34.567") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)

        )
        Spacer(modifier = Modifier.height(16.dp))



        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = viewModel.isNotificationEnabled,
                onCheckedChange = { viewModel.onNotificationCheckedChange(it) }
            )
            Text(text = "通知")

            Spacer(modifier = Modifier.width(16.dp))

            Checkbox(
                checked = viewModel.isVibrationEnabled,
                onCheckedChange = { viewModel.onVibrationCheckedChange(it) }
            )
            Text(text = "バイブレーション")
        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.saveRoute() // 保存機能を呼び出す
            toBackScreen() // 戻る処理
        }) {
            Text(text = "保存")
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
