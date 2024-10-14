package net.harutiro.trainalert2.core.presenter.map

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.MarkerOptions
import net.harutiro.trainalert2.features.map.repository.MapEffects

@Composable
fun MapScreen(
    viewModel: MapViewModel = viewModel()
){
    //MapEffectの情報受け取ってGoogleMap呼び出し
    fun mapViewTest(markerOptions: MarkerOptions){
    }

}
