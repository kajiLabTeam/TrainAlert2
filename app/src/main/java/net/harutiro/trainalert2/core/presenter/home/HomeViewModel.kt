package net.harutiro.trainalert2.core.presenter.home

import android.util.Log
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    val TAG = "HomeViewModel"

    fun test(){
        Log.d(TAG,"ホーム画面のViewModel")
    }
}
