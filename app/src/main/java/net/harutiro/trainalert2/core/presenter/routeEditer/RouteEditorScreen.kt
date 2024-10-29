package net.harutiro.trainalert2.core.presenter.routeEditer

import net.harutiro.trainalert2.core.presenter.routeEditer.RouteEditorViewModel
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.harutiro.trainalert2.core.presenter.home.HomeViewModel
import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable


@Composable
fun RouteEditScreen(
    toBackScreen: () -> Unit,
    homeViewModel: HomeViewModel = viewModel()
) {
    val viewModel: RouteEditorViewModel = viewModel(factory = RouteEditorViewModelFactory(homeViewModel))
    val context = LocalContext.current

    // toastMessageが変更されたときにトーストを表示
    LaunchedEffect(viewModel.toastMessage) {
        showToast(context, viewModel.toastMessage)
    }

    //戻るボタンが押された時の確認用
    var showBackConfirmationDialog by rememberSaveable { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "ルート編集",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )

        // ルート名入力フィールド
        RouteTextField(
            value = viewModel.title,
            onValueChange = { viewModel.title = it },
            label = "ルート名"
        )

        RouteTextField(
            value = viewModel.startLongitude,
            onValueChange = { viewModel.startLongitude = it },
            label = "出発地点の経度",
            placeholder = "例: 123.456"
        )

        RouteTextField(
            value = viewModel.startLatitude,
            onValueChange = { viewModel.startLatitude = it },
            label = "出発地点の緯度",
            placeholder = "例: 34.567"
        )

        RouteTextField(
            value = viewModel.endLongitude,
            onValueChange = { viewModel.endLongitude = it },
            label = "到着地点の経度",
            placeholder = "例: 123.456"
        )

        RouteTextField(
            value = viewModel.endLatitude,
            onValueChange = { viewModel.endLatitude = it },
            label = "到着地点の緯度",
            placeholder = "例: 34.567"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // アラート方法（通知、バイブレーション）の選択
        AlertMethodSelection(viewModel)

        Spacer(modifier = Modifier.height(16.dp))

        // 有効化トグルスイッチ
        ToggleActivation(viewModel)

        Spacer(modifier = Modifier.height(24.dp))

        // 保存・戻るボタン
        ActionButtons(
            onSaveClick = {
                viewModel.saveRoute {
                    showToast(context, viewModel.toastMessage)
                    toBackScreen()
                }
            },
            onBackClick = { showBackConfirmationDialog = true }
        )
    }
    // 確認ダイアログの実装
    if (showBackConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showBackConfirmationDialog = false },
            title = { Text(text = "確認") },
            text = { Text(text = "保存せずに戻りますか？") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showBackConfirmationDialog = false
                        toBackScreen() // 前の画面に戻る
                    }
                ) {
                    Text("はい")
                }
            },
            dismissButton = {
                TextButton(onClick = { showBackConfirmationDialog = false }) {
                    Text("いいえ")
                }
            }
        )
    }
}

@Composable
fun RouteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = ""
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
fun AlertMethodSelection(viewModel: RouteEditorViewModel) {
    Column {
        Text(
            text = "アラート方法",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = viewModel.isNotificationEnabled,
                onCheckedChange = { viewModel.onNotificationCheckedChange(it) }
            )
            Text(text = "通知", modifier = Modifier.padding(start = 8.dp))

            Spacer(modifier = Modifier.width(24.dp))

            Checkbox(
                checked = viewModel.isVibrationEnabled,
                onCheckedChange = { viewModel.onVibrationCheckedChange(it) }
            )
            Text(text = "バイブレーション", modifier = Modifier.padding(start = 8.dp))
        }
    }
}

@Composable
fun ToggleActivation(viewModel: RouteEditorViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "ルートを有効にする",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = viewModel.isEnabled,
            onCheckedChange = { viewModel.isEnabled = it }
        )
    }
}

@Composable
fun ActionButtons(onSaveClick: () -> Unit, onBackClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            Text(text = "保存", textAlign = TextAlign.Center)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = onBackClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            Text(text = "戻る", textAlign = TextAlign.Center)
        }
    }
}

// トーストを表示するための関数
fun showToast(context: Context, message: String?) {
    message?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }
}
