package codexsample.authentication.ui.features.signup

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codexsample.authentication.R
import codexsample.authentication.ui.common.PrimaryButton
import codexsample.authentication.ui.common.SignInWithSource
import codexsample.authentication.ui.common.openURL
import codexsample.authentication.ui.features.login.LoginSource

sealed class SignUpUIState {
    data object Idle : SignUpUIState()
    data object Loading:SignUpUIState()
    data object Success : SignUpUIState()
    data class Error(val exception: Exception) : SignUpUIState()
}

@Composable
fun SignUpScreen(
    shouldShowLoading :Boolean,
    onLoginClicked: (LoginSource) -> Unit,
    onSignInClicked: () -> Unit,
    onCreateAccountClicked: (name: String, email: String, password: String) -> Unit
) {
    var nameInputValue by remember { mutableStateOf("") }
    var emailInputValue by remember { mutableStateOf("") }
    var passwordInputValue by remember { mutableStateOf("") }
    var shouldShowPasswordPlainText by remember { mutableStateOf(false) }
    var privacyTermAccepted by remember { mutableStateOf(false) }
    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        contentAlignment = Alignment.Center
    ){
        Column(
            Modifier
                .fillMaxSize()
        ) {
            SignInWithSource(
                stringResource(R.string.sign_up_label),
                stringResource(R.string.sign_up_subtitle),
                onLoginClicked
            )
            OutlinedTextField(value = nameInputValue,
                onValueChange = {
                    nameInputValue = it
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
                        text = stringResource(R.string.place_holder_name),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                        ),
                    )
                })
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

            // privacy checkbox
            Row(
                Modifier.padding(start = 12.dp, end = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.size(48.dp), contentAlignment = Alignment.Center) {
                    Checkbox(
                        checked = privacyTermAccepted,
                        onCheckedChange = {
                            privacyTermAccepted = it
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary,
                            uncheckedColor = MaterialTheme.colorScheme.secondaryContainer,
                            checkmarkColor = Color.White
                        ),
                    )
                }
                TermPrivacyLinkClickableText(
                    Modifier
                        .padding(start = 2.dp)
                        .fillMaxWidth()
                )
            }
            PrimaryButton(
                modifier = Modifier
                    .padding(top = 20.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth(),
                label = stringResource(R.string.create_account_btn_label)
            ) {
                if (!shouldShowLoading && nameInputValue.isNotBlank() && emailInputValue.isNotBlank() && passwordInputValue.isNotBlank()) {
                    onCreateAccountClicked.invoke(nameInputValue, emailInputValue, passwordInputValue)
                }
            }
            Row(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .clickable {
                        onSignInClicked.invoke()
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.have_account),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.secondary,
                    ),
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 2.dp)
                )
                Text(
                    text = stringResource(R.string.sign_in_label),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF3461FD),
                    ),
                )
            }
        }
        if(shouldShowLoading){
            CircularProgressIndicator()
        }
    }

}

@Composable
private fun TermPrivacyLinkClickableText(modifier: Modifier = Modifier) {
    val termPrivacyDesc = stringResource(R.string.term_privacy_desc)
    val termHighLight = stringResource(R.string.term_highlight)
    val privacyHighlight = stringResource(R.string.privacy_highlight)
    val termIndex = termPrivacyDesc.indexOf(termHighLight)
    val privacyIndex = termPrivacyDesc.indexOf(privacyHighlight)
    val context = LocalContext.current
    val annotatedText = buildAnnotatedString {
        append(termPrivacyDesc)
        if (termIndex != -1) {
            addStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary
                ), start = termIndex, end = termIndex + termHighLight.length
            )
            addStringAnnotation(
                "term-url",
                annotation = stringResource(R.string.term_link),
                start = termIndex,
                end = termIndex + termHighLight.length
            )
        }
        if (privacyIndex != -1) {
            addStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary
                ), start = privacyIndex, end = privacyIndex + privacyHighlight.length
            )
            addStringAnnotation(
                "privacy-url",
                annotation = stringResource(R.string.privacy_link),
                start = privacyIndex,
                end = privacyIndex + privacyHighlight.length
            )
        }
    }
    ClickableText(
        annotatedText, modifier = modifier, style = MaterialTheme.typography.titleSmall.copy(
            fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary
        )
    ) { offset ->
        annotatedText.getStringAnnotations(tag = "term-url", start = offset, end = offset)
            .firstOrNull()?.let {
                context.openURL(it.item)
            }
        annotatedText.getStringAnnotations(tag = "privacy-url", start = offset, end = offset)
            .firstOrNull()?.let {
                context.openURL(it.item)
            }
    }
}

