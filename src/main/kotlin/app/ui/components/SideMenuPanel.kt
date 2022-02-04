package app.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import app.maxSidePanelHeight

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> SideMenuPanel(
    title: String,
    icon: Painter? = null,
    items: List<T>,
    itemsCountForMaxHeight: Int = items.count(),
    itemContent: @Composable (T) -> Unit,
) {
    val maxHeight = remember(items) { maxSidePanelHeight(itemsCountForMaxHeight) }

    VerticalExpandable(
        header = {
            SideMenuEntry(
                text = title,
                icon = icon,
                itemsCount = items.count()
            )
        },
    ) {
        ScrollableLazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = maxHeight.dp)
                .background(MaterialTheme.colors.background)
        ) {
            items(items) { item ->
                itemContent(item)
            }
        }
    }
}