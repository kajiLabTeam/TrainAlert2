package net.harutiro.trainalert2.core.presenter.routeEditer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
            onValueChange = {
                if (it.matches(Regex("^\\d*\\.?\\d*\$"))) { //少数のみ入力にする
                    viewModel.startLongitude = if (it.matches(Regex("^\\d+$"))) "$it.0" else it
                    //もし整数で入力された場合、.0をつける
                }
            },
            label = { Text("出発地点の経度") },
            placeholder = { Text("例: 123.456") }
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 出発地点の緯度入力フィールド
        TextField(
            value = viewModel.startLatitude,
            onValueChange = {
                if (it.matches(Regex("^\\d*\\.?\\d*\$"))) {//少数のみ入力にする
                    viewModel.startLatitude = if (it.matches(Regex("^\\d+$"))) "$it.0" else it
                    //もし整数で入力された場合、.0をつける
                }
            },
            label = { Text("出発地点の緯度") },
            placeholder = { Text("例: 34.567") }
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 到着地点の経度入力フィールド
        TextField(
            value = viewModel.endLongitude,
            onValueChange = {
                if (it.matches(Regex("^\\d*\\.?\\d*\$"))) {//少数のみ入力にする
                    viewModel.endLongitude = if (it.matches(Regex("^\\d+$"))) "$it.0" else it
                    //もし整数で入力された場合、.0をつける
                }
            },
            label = { Text("到着地点の経度") },
            placeholder = { Text("例: 123.456") }
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 到着地点の緯度入力フィールド
        TextField(
            value = viewModel.endLatitude,
            onValueChange = {
                if (it.matches(Regex("^\\d*\\.?\\d*\$"))) {//少数のみ入力にする
                    viewModel.endLatitude = if (it.matches(Regex("^\\d+$"))) "$it.0" else it
                    //もし整数で入力された場合、.0をつける
                }
            },
            label = { Text("到着地点の緯度") },
            placeholder = { Text("例: 34.567") }
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
