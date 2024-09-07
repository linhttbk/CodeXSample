package codexsample.authentication.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codexsample.authentication.R
import codexsample.authentication.ui.features.login.LoginSource

@Composable
fun ColumnScope.SignInWithSource(
    title:String,
    subtitle:String,
    onLoginClicked: (LoginSource)->Unit,

){
    Text(
        text = title,
        style = MaterialTheme.typography.headlineLarge.copy(
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .padding(top = 80.dp)
            .align(Alignment.CenterHorizontally)
    )
    Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall.copy(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
    )
    Row(
        Modifier
            .padding(start = 24.dp, top = 24.dp, end = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        CommonSignInButton(
            sourceLogoResId = R.drawable.ic_facebook_source, sourceTitle = stringResource(
                R.string.facebook_app_title
            )
        ) {
            onLoginClicked.invoke(LoginSource.Facebook)
        }
        CommonSignInButton(
            sourceLogoResId = R.drawable.ic_google_source, sourceTitle = stringResource(
                R.string.google_app_title
            )
        ) {
            onLoginClicked.invoke(LoginSource.Google)
        }
    }
    Row(
        Modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            Modifier
                .weight(1f)
                .height(1.dp)
                .background(Color(0xFFE0E5EC))
        )
        Text(
            text = stringResource(R.string.common_or),
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 11.dp)
        )
        Spacer(
            Modifier
                .weight(1f)
                .height(1.dp)
                .background(Color(0xFFE0E5EC))
        )
    }
}