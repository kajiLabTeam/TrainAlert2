package net.harutiro.trainalert2.core.presenter.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity

@Composable
fun HomeScreen(
    toRouteEditor: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    // RouteListをStateFlowから取得
    val routeList by viewModel.routeList.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // 全体に余白を追加
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // 経路作成画面へ遷移するボタン
        Button(
            onClick = { toRouteEditor() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "経路作成画面へ")
        }

        Spacer(modifier = Modifier.height(16.dp)) // ボタンとリストの間にスペース追加

        // LazyColumnでルートのリストを表示
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp) // 各項目間にスペースを追加
        ) {
            items(routeList) { route: RouteEntity ->
                RouteItem(route = route) // 各ルートの情報を表示するためのComposable
            }
        }
    }
}

// ルートの詳細表示用のComposable
@Composable
fun RouteItem(route: RouteEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), // カード全体に余白を追加
        elevation = CardDefaults.cardElevation(4.dp) // カードに影をつけて視覚的に強調
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // カード内の余白
        ) {
            Text(text = "ルート名: ${route.title ?: "Unnamed Route"}")
            Text(text = "出発地点の経度: ${route.startLongitude}")
            Text(text = "出発地点の緯度: ${route.startLatitude}")
            Text(text = "到着地点の経度: ${route.endLongitude}")
            Text(text = "到着地点の緯度: ${route.endLatitude}")
            Text(text = "アラート方法: ${route.alertMethods ?: "None"}")
        }
    }
}
