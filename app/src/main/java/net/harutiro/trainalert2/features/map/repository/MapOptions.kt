package net.harutiro.trainalert2.features.map.repository

import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng

class MapOptions {
        fun redCircle(CenterLatitude: Double, CenterLongitude: Double, judgerange:Double): CircleOptions {
            val circleOptions = CircleOptions()
                .center(LatLng(CenterLatitude, CenterLongitude))//end_latitude,end_longitude
                .radius(judgerange) // In meters
                .fillColor(0x40FF0000)
                .strokeColor(0xFFFF0000.toInt())
                .strokeWidth(5f)
            return circleOptions
        }
}