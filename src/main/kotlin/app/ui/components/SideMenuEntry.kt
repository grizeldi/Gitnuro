package app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.theme.secondaryTextColor

@Composable
fun SideMenuEntry(
    text: String,
) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .clickable(onClick = {}),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            text = text,
            modifier = Modifier
                .padding(horizontal = 8.dp),
            maxLines = 1,
            fontSize = 14.sp,
            color = MaterialTheme.colors.secondaryTextColor,
            overflow = TextOverflow.Ellipsis,
        )
    }
}