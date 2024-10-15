package net.harutiro.trainalert2.core.presenter.map


import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import net.harutiro.trainalert2.features.map.api.MapApi
import net.harutiro.trainalert2.features.map.repository.MapOptions
import net.harutiro.trainalert2.features.map.repository.MapRepository


internal class MapViewModel:ComponentActivity() {

    //val TAG = "MapViewModel"

}