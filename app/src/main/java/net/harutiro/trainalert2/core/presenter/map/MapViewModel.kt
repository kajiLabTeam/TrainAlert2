package net.harutiro.trainalert2.core.presenter.map

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import net.harutiro.trainalert2.features.map.api.MapApi
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import net.harutiro.trainalert2.features.map.repository.MapOptions
import net.harutiro.trainalert2.features.map.repository.MapRepository


internal class MapViewModel:ComponentActivity() {

    val TAG = "MapViewModel"

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        //フルスクリーン表示
//
//        val Context = LocalContext.current
//        setContent {
//            //マップ表示
//            MapScreen(
//                mapApi = MapApi(context = Context),
//                mapRepository = MapRepository(),
//                mapOptions = MapOptions()
//            )
//        }
//    }
}
