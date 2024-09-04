package codexsample.authentication.ui.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codexsample.authentication.R
import codexsample.authentication.ui.common.PrimaryButton
import codexsample.authentication.ui.theme.AuthenticationTheme

@Composable
fun LoginScreen(
    onLoginClicked: (LoginSource) -> Unit
) {
    var emailInputValue by remember { mutableStateOf("") }
    var passwordInputValue by remember { mutableStateOf("") }
    var shouldShowPasswordPlainText by remember { mutableStateOf(false) }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        Text(
            text = stringResource(R.string.sign_in_label),
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
            text = stringResource(R.string.sign_in_subtitle),
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


        OutlinedTextField(value = emailInputValue,
            onValueChange = {
                emailInputValue = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedBorderColor = Color(0xFF3461FD),
            ),
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            placeholder = {
                Text(
                    text = stringResource(R.string.place_holder_email),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                    ),
                )
            })

        OutlinedTextField(value = passwordInputValue,
            onValueChange = {
                passwordInputValue = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedBorderColor = Color(0xFF3461FD),
            ),
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            trailingIcon = {
                if (passwordInputValue.isNotEmpty()) {
                    Image(painter = painterResource(if (shouldShowPasswordPlainText) R.drawable.visibility_off else R.drawable.visible_password),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier.clickable {
                            shouldShowPasswordPlainText = !shouldShowPasswordPlainText
                        })
                }
            },
            visualTransformation = if (shouldShowPasswordPlainText) VisualTransformation.None else PasswordVisualTransformation(),
            placeholder = {
                Text(
                    text = stringResource(R.string.place_holder_password),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                    ),
                )
            })
        Box(Modifier.align(Alignment.End).clickable {
            //TODO handle forgot password
        }) {
            Text(
                text = stringResource(R.string.forgot_password),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.secondary,
                ),
                modifier = Modifier.padding(end = 34.dp, start = 34.dp, top = 10.dp, bottom = 10.dp)
            )
        }

        PrimaryButton(
            modifier = Modifier
                .padding(top = 19.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            label = stringResource(R.string.login_btn_label)
        ) {
            //todo handle login clicked
        }
        Row (modifier = Modifier.padding(start = 24.dp).clickable {
            //todo handle sign up
        },verticalAlignment = Alignment.CenterVertically){
            Text(
                text = stringResource(R.string.no_account),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.secondary,
                ),
                modifier = Modifier.padding( start = 16.dp, top = 16.dp, bottom = 16.dp,end =2.dp)
            )
            Text(
                text = stringResource(R.string.sign_up_label),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF3461FD),
                ),
            )
        }


    }
}

@Composable
private fun RowScope.CommonSignInButton(
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

@Preview
@Composable
fun LoginScreenPreview() {
    AuthenticationTheme {
        LoginScreen(onLoginClicked = {

        })
    }
}