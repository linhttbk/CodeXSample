package codexsample.authentication.ui.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codexsample.authentication.ui.common.PrimaryButton

@Composable
fun HomeScreen(userEmail: String?, onSignOutClicked: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        if(userEmail != null){
            Text(
                "Hello $userEmail",
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )
            PrimaryButton(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 40.dp)
                    .fillMaxWidth(),
                label = "Sign Out",
                onClicked = onSignOutClicked
            )
        }
    }
}