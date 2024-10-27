package net.harutiro.trainalert2.core.presenter.home

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.harutiro.trainalert2.TrainAlertApplication // Ensure to import your application context
import net.harutiro.trainalert2.features.notification.api.NotificationApi
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity

@Composable
fun HomeScreen(
    toRouteEditor: (Int?) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    val routeList by viewModel.routeList.collectAsState(initial = emptyList())
    val (showDeleteDialog, setShowDeleteDialog) = remember { mutableStateOf<Pair<RouteEntity, Boolean>?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Button(onClick = { toRouteEditor(null) }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "経路作成画面へ")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(routeList) { route: RouteEntity ->
                RouteItem(
                    route = route,
                    onEdit = {
                        toRouteEditor(route.id)
                    },
                    onDelete = {
                        setShowDeleteDialog(Pair(route, true)) // ダイアログ表示
                    }
                )
            }
        }
    }

    // 削除確認ダイアログ
    showDeleteDialog?.let { (route, isVisible) ->
        if (isVisible) {
            AlertDialog(
                onDismissRequest = { setShowDeleteDialog(null) },
                title = { Text(text = "削除確認") },
                text = { Text(text = "${route.title ?: "Unnamed Route"} を削除しますか？") },
                confirmButton = {
                    Button(onClick = {
                        viewModel.deleteRoute(route) // 削除メソッドを呼び出す
                        setShowDeleteDialog(null) // ダイアログを閉じる
                    }) {
                        Text("削除")
                    }
                },
                dismissButton = {
                    Button(onClick = { setShowDeleteDialog(null) }) {
                        Text("キャンセル")
                    }
                }
            )
        }
    }
}