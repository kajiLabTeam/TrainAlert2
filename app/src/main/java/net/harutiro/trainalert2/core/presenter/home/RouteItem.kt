package net.harutiro.trainalert2.core.presenter.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity

@Composable
fun RouteItem(route: RouteEntity, onEdit: () -> Unit, onDelete: () -> Unit) {
    // アラート方法の表示を変換
    val alertMethodDisplay = when (route.alertMethods) {
        "Notification" -> "通知"
        "Vibration" -> "バイブレーション"
        else -> "None"
    }

    // 有効かどうかの表示を変換
    val isEnabledDisplay = if (route.isEnabled) "オン" else "オフ"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "ルート名: ${route.title ?: "Unnamed Route"}")
            Text(text = "出発地点の経度: ${route.startLongitude}")
            Text(text = "出発地点の緯度: ${route.startLatitude}")
            Text(text = "到着地点の経度: ${route.endLongitude}")
            Text(text = "到着地点の緯度: ${route.endLatitude}")
            Text(text = "アラート方法: $alertMethodDisplay")
            Text(text = "オン・オフ: $isEnabledDisplay")

            // 編集ボタン
            Row {
                Button(onClick = onEdit, modifier = Modifier.padding(end = 8.dp)) {
                    Text("編集")
                }

                // 削除ボタン
                Button(onClick = onDelete) {
                    Text("削除")
                }
            }
        }
    }
}
