package net.harutiro.trainalert2.features.location.repository

import kotlin.math.*

class DistanceJudgement {
    //lat1:現在地  lat2:目的地
    fun within600m(lat1: Double, lon1: Double, lat2:Double, lon2: Double): Boolean {
        val R = 6371000.0 // 地球の半径（メートル）

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        //距離判定用変数
        //R*c=距離（m単位）
        val judgement:Boolean
        val judgerange: Double = 600.0
        if(R * c < judgerange){
            judgement = true
        }else{
            judgement = false
        }
        return judgement
    }
}