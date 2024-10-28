package net.harutiro.trainalert2.core.presenter.routeEditer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.harutiro.trainalert2.core.presenter.home.HomeViewModel

class RouteEditorViewModelFactory(
    private val homeViewModel: HomeViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RouteEditorViewModel::class.java)) {
            return RouteEditorViewModel(homeViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
