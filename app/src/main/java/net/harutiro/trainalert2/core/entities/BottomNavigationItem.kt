package net.harutiro.trainalert2.core.entities

import androidx.compose.ui.graphics.vector.ImageVector
import net.harutiro.trainalert2.core.router.BottomNavigationBarRoute

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val path: BottomNavigationBarRoute
)