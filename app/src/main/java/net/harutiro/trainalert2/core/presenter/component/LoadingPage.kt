package net.harutiro.trainalert2.core.presenter.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.harutiro.trainalert2.core.widgets.Center

@Composable
fun LoadingPage(
    isLoading: Boolean,
    content: @Composable () -> Unit,
){
    if(isLoading){
        Center{
            CircularProgressIndicator(
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }else{
        content()
    }
}

@Preview
@Composable
fun LoadingScrollColumnPreview(){
    LoadingPage(
        isLoading = true,
        content = {},
    )
}