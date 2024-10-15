package net.harutiro.trainalert2.core.presenter.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.harutiro.trainalert2.features.room.routeDB.entities.RouteEntity // RouteEntityのimportを確認

@Composable
fun HomeScreen(
    toRouteEditor: () -> Unit,
    viewModel: HomeViewModel = viewModel()
){
    // RouteListをStateFlowから取得
    val routeList by viewModel.routeList.collectAsState(initial = emptyList())

    Column {
        Text(text = "HomeScreen")
        Button(
            onClick = {
                viewModel.test()
            }
        ) {
            Text(text = "Logのテスト")
        }
        Button(
            onClick = {
                toRouteEditor()
            }
        ) {
            Text(text = "経路作成画面へ")
        }

        // LazyColumnでルートのリストを表示
        LazyColumn {
            items(routeList) { route: RouteEntity -> // RouteEntity型を指定
                // 各ルートの情報を表示
                Column {
                    Text(text = "ルート名: ${route.title ?: "Unnamed Route"}")
                    Text(text = "出発地点の経度: ${route.startLongitude}")
                    Text(text = "出発地点の緯度: ${route.startLatitude}")
                    Text(text = "到着地点の経度: ${route.endLongitude}")
                    Text(text = "到着地点の緯度: ${route.endLatitude}")
                    Text(text = "アラート方法: ${route.alertMethods ?: "None"}")
                    Spacer(modifier = Modifier.height(16.dp))  // 項目間にスペースを追加
                }
            }
        }
    }
}
