package net.harutiro.trainalert2.features.map.repository

import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapOptions {
    fun MapShapeOptions(startCenterLatitude:Double,startCenterLongitude: Double,endCenterLatitude:Double,endCenterLongitude: Double){
        val judgerange = 600.0
        val circleOptions = CircleOptions()
            .center(LatLng(endCenterLatitude, endCenterLongitude))//end_latitude,end_longitude
            .radius(judgerange) // In meters
            .fillColor(0x40FF0000)
            .strokeColor(0xFFFF0000.toInt())
            .strokeWidth(5f)
        val startMarkerOptions = MarkerOptions()
            .position(LatLng(startCenterLatitude, startCenterLongitude))//start_latitude,start_longitude
        val endMarkerOptions = MarkerOptions()
            .position(LatLng(endCenterLatitude, endCenterLongitude))//end_latitude,end_longitude
    }
}