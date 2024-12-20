package net.harutiro.trainalert2.core.presenter.routeEditer

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import net.harutiro.trainalert2.core.presenter.LocationSelect.LocationSelectScreen
import net.harutiro.trainalert2.core.presenter.home.HomeViewModel
import java.util.Locale


@Composable
fun RouteEditScreen(
    toBackScreen: () -> Unit,
    homeViewModel: HomeViewModel = viewModel(),
    routeId: Int? = null
) {
    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedTitle by remember { mutableStateOf("") }
    var onLocationSelected: ((LatLng) -> Unit)? by remember { mutableStateOf(null) }

    val viewModel: RouteEditorViewModel = viewModel(factory = RouteEditorViewModelFactory(homeViewModel))
    val context = LocalContext.current

    // ダイアログ表示フラグ
    var isDialogOpen by remember { mutableStateOf(false) }

    // ルートが渡された場合は、データをロードする
    LaunchedEffect(routeId) {
        routeId?.let {
            viewModel.loadRoute(routeId)
        }
    }

    // toastMessageが変更されたときにトーストを表示
    LaunchedEffect(viewModel.toastMessage) {
        showToast(context, viewModel.toastMessage)
    }

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

        // 出発地点
        LocationSelectionCard(
            title = "出発地点の緯度・経度",
            latitude = viewModel.startLatitude,
            longitude = viewModel.startLongitude,
            onClick = {
                selectedTitle = "出発地点をタップしてください"
                onLocationSelected = { location ->
                    viewModel.startLatitude = location.latitude.toString()
                    viewModel.startLongitude = location.longitude.toString()
                }
                isDialogVisible = true
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 到着地点
        LocationSelectionCard(
            title = "到着地点の緯度・経度",
            latitude = viewModel.endLatitude,
            longitude = viewModel.endLongitude,
            onClick = {
                selectedTitle = "到着地点をタップしてください"
                onLocationSelected = { location ->
                    viewModel.endLatitude = location.latitude.toString()
                    viewModel.endLongitude = location.longitude.toString()
                }
                isDialogVisible = true
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "アラート方法を選択：")
        Spacer(modifier = Modifier.height(16.dp))

        // アラート方法（通知、バイブレーション）の選択
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = viewModel.isNotificationEnabled,
                onCheckedChange = { checked ->
                    viewModel.onNotificationCheckedChange(checked)
                }
            )
            Text(text = "通知")

            Spacer(modifier = Modifier.width(24.dp))

            Checkbox(
                checked = viewModel.isVibrationEnabled,
                onCheckedChange = { checked ->
                    viewModel.onVibrationCheckedChange(checked)
                }
            )
            Text(text = "バイブレーション")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = viewModel.isLightEnabled,
                onCheckedChange = { checked ->
                    viewModel.onLightCheckedChange(checked)
                }
            )
            Text(text = "光の点滅")

            Spacer(modifier = Modifier.width(24.dp))

            Checkbox(
                checked = viewModel.isSoundEnabled,
                onCheckedChange = { checked ->
                    viewModel.onSoundCheckedChange(checked)
                }
            )
            Text(text = "音楽")
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

        Spacer(modifier = Modifier.height(24.dp))


        // 保存ボタン
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
            onClick = {
                isDialogOpen = true
            },
            modifier = Modifier.weight(1f)
            ) {
            Text(text = "←戻る")
            }
            Button(
                onClick = {
                    Log.d("RouteEditor", "Save button clicked")

                    viewModel.saveRoute(
                        isNewRoute = routeId == -1
                    ) {
                        showToast(context, viewModel.toastMessage)
                        // ホーム画面に戻る
                        toBackScreen()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "保存")
            }

            Spacer(modifier = Modifier.width(16.dp))

        }
    }
    // ダイアログ表示
    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = { isDialogOpen = false },
            text = { Text("保存せずに戻りますか？") },
            confirmButton = {
                Button(onClick = {
                    isDialogOpen = false
                    toBackScreen() // ホーム画面に戻る処理
                }) {
                    Text("はい")
                }
            },
            dismissButton = {
                Button(onClick = { isDialogOpen = false }) {
                    Text("キャンセル")
                }
            }
        )
    }

    // ポップアップの表示
    if (isDialogVisible) {
        ShowLocationSelectDialog(
            title = selectedTitle,
            onDismissRequest = { isDialogVisible = false },
            onLocationSelected = { location ->
                onLocationSelected?.invoke(location)
                isDialogVisible = false // ダイアログを閉じる
            }
        )
    }
}

// 位置情報選択用のカードコンポーネント
@Composable
fun LocationSelectionCard(
    title: String,
    latitude: String,
    longitude: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    var convertLatitude = ""
                    var convertLongitude = ""
                    if(latitude.isNotEmpty()){
                        convertLongitude = String.format(
                            Locale.JAPAN,
                            "%.6f",
                            longitude.toDouble()
                        )
                    }
                    if(longitude.isNotEmpty()){
                        convertLatitude = String.format(
                            Locale.JAPAN,
                            "%.6f",
                            latitude.toDouble()
                        )
                    }
                    Text(text = "緯度：${convertLatitude}", fontSize = 14.sp)
                    Text(text = "経度：${convertLongitude}", fontSize = 14.sp)
                }
                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("位置を選択")
                }
            }
        }
    }
}

@Composable
fun ShowLocationSelectDialog(
    title: String,
    onDismissRequest:()->Unit ,
    onLocationSelected: (LatLng) -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.7f)
        ) {
            Column{
                Row{
                    IconButton(
                        onClick = {
                            onDismissRequest()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    Text(
                        text=title,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(8.dp),
                    )
                }
                LocationSelectScreen(
                    toBackEditor = { location ->
                        onLocationSelected(location)
                    }
                )
            }
        }
    }
}

// トーストを表示するための関数
fun showToast(context: Context, message: String?) {
    message?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }
}