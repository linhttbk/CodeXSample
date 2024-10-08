package codexsample.authentication.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryButton(modifier: Modifier = Modifier, label: String, onClicked: () -> Unit) {
    Box(modifier = Modifier
        .then(modifier)
        .clickable {
            onClicked.invoke()
        }
        .background(Color(0xFF3461FD), RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center) {
        Text(
            text = label,
            modifier = Modifier.padding(vertical = 18.dp),
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 16.sp, color = Color.White)
        )
    }
}
@Composable
 fun RowScope.CommonSignInButton(
    sourceLogoResId: Int, sourceTitle: String, onClicked: () -> Unit
) {
    Row(
        Modifier
            .weight(1f)
            .clickable {
                onClicked.invoke()
            }
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(14.dp)
            ), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(sourceLogoResId),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .size(24.dp)
        )
        Text(
            text = sourceTitle,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            ),
        )
    }
}