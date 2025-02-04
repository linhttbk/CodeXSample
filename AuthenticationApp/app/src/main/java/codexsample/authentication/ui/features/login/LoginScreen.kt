package codexsample.authentication.ui.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codexsample.authentication.R
import codexsample.authentication.ui.common.PrimaryButton
import codexsample.authentication.ui.common.SignInCredentialState
import codexsample.authentication.ui.common.SignInWithSource
import codexsample.authentication.ui.theme.AuthenticationTheme

@Composable
fun LoginScreen(
    signInState: SignInCredentialState,
    onLoginClicked: (LoginSource) -> Unit,
    onSignUpClicked: () -> Unit
) {
    var emailInputValue by remember { mutableStateOf("") }
    var phoneInputValue by remember { mutableStateOf("") }
    var passwordInputValue by remember { mutableStateOf("") }
    var shouldShowPasswordPlainText by remember { mutableStateOf(false) }
    var isShowPhoneLogin by remember {
        mutableStateOf(false)
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFFFF))
        ) {
            SignInWithSource(
                stringResource(R.string.sign_in_label),
                stringResource(R.string.sign_in_subtitle),
                onLoginClicked
            )
            if (isShowPhoneLogin) {
                OutlinedTextField(
                    value = phoneInputValue,
                    onValueChange = {
                        phoneInputValue = it
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
                            text = stringResource(R.string.place_holder_phone_number),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                            ),
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
            } else {
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
            }

            Box(
                Modifier
                    .align(Alignment.End)
                    .clickable {
                        //TODO handle forgot password
                    }) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.secondary,
                    ),
                    modifier = Modifier.padding(
                        end = 34.dp, start = 34.dp, top = 10.dp, bottom = 10.dp
                    )
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
            Row(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .clickable {
                        onSignUpClicked.invoke()
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.no_account),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.secondary,
                    ),
                    modifier = Modifier.padding(
                        start = 16.dp, top = 16.dp, bottom = 16.dp, end = 2.dp
                    )
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
        if (signInState == SignInCredentialState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}


@Preview
@Composable
fun LoginScreenPreview() {
    AuthenticationTheme(dynamicColor = false) {
        LoginScreen(onLoginClicked = {

        }, onSignUpClicked = {

        }, signInState = SignInCredentialState.Initialized)
    }
}