package net.harutiro.trainalert2.core.presenter.routeEditer

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        // ルート名入力フィールド
        TextField(
            value = viewModel.title,
            onValueChange = { viewModel.title = it },
            label = { Text("ルート名") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 出発地点の経度入力フィールド
        TextField(
            value = viewModel.startLongitude,
            onValueChange = { viewModel.startLongitude = it },
            label = { Text("出発地点の経度") },
            placeholder = { Text("例: 123.456") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 出発地点の緯度入力フィールド
        TextField(
            value = viewModel.startLatitude,
            onValueChange = { viewModel.startLatitude = it },
            label = { Text("出発地点の緯度") },
            placeholder = { Text("例: 34.567") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 到着地点の経度入力フィールド
        TextField(
            value = viewModel.endLongitude,
            onValueChange = { viewModel.endLongitude = it },
            label = { Text("到着地点の経度") },
            placeholder = { Text("例: 123.456") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 到着地点の緯度入力フィールド
        TextField(
            value = viewModel.endLatitude,
            onValueChange = { viewModel.endLatitude = it },
            label = { Text("到着地点の緯度") },
            placeholder = { Text("例: 34.567") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        // アラート方法（通知、バイブレーション）の選択
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = viewModel.isNotificationEnabled,
                onCheckedChange = { viewModel.onNotificationCheckedChange(it) }
            )
            Text(text = "通知")

            Spacer(modifier = Modifier.width(24.dp))

            Checkbox(
                checked = viewModel.isVibrationEnabled,
                onCheckedChange = { viewModel.onVibrationCheckedChange(it) }
            )
            Text(text = "バイブレーション")
        }

        // ルートの有効/無効を選択するトグルスイッチ
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("ルートを有効にする：")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = viewModel.isEnabled,
                onCheckedChange = { viewModel.isEnabled = it }
            )
        }

        Spacer(modifier = Modifier.height(24.dp)) // ボタンの後に余白






        // 保存ボタン
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    Log.d("RouteEditor", "Save button clicked")
                    viewModel.saveRoute() // 保存機能を呼び出す
                    toBackScreen() // 戻る処理
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "保存")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    toBackScreen()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "戻る")
            }
        }
    }
}