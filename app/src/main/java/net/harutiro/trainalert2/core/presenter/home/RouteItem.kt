package net.harutiro.trainalert2.core.presenter.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Divider
import androidx.compose.foundation.layout.Arrangement
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity

@Composable
fun RouteItem(route: RouteEntity, onEdit: () -> Unit, onDelete: () -> Unit) {
    // アラート方法の表示を変換
    val alertMethodDisplay = buildString {
        if (route.alertMethods == 0) {
            append("通知")
        } else {
            if (route.alertMethods and RouteEntity.NOTIFICATION != 0) append("通知")
            if (route.alertMethods and RouteEntity.VIBRATION != 0) {
                if (isNotEmpty()) append("、")
                append("バイブレーション")
            }
            if (route.alertMethods and RouteEntity.LIGHT != 0) {
                if (isNotEmpty()) append("、")
                append("光の点滅")
            }
            if (route.alertMethods and RouteEntity.SOUND != 0) {
                if (isNotEmpty()) append("、")
                append("音楽")
            }
        }
    }

    // 有効かどうかの表示を変換
    val isEnabledDisplay = if (route.isEnabled) "オン" else "オフ"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "ルート名",
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium
            )
            Text(
                text = route.title ?: "Unnamed Route",
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // 出発地点
            Text(
                text = "出発地点",
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "緯度: ${route.startLatitude}", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
                Text(text = "経度: ${route.startLongitude}", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // 到着地点
            Text(
                text = "到着地点",
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "緯度: ${route.endLatitude}", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
                Text(text = "経度: ${route.endLongitude}", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // アラート方法
            Text(
                text = "アラート方法",
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium
            )
            Text(text = alertMethodDisplay, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // オン・オフ
            Text(
                text = "オン・オフ",
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium
            )
            Text(text = isEnabledDisplay, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)

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