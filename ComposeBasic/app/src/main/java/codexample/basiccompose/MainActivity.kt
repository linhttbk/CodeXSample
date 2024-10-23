package codexample.basiccompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codexample.basiccompose.ui.theme.ComposeBasicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeBasicTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        TextFieldExample()
                    }
                }
            }
        }
    }

    @Composable
    private fun TextExample() {
        // handle overflow????
        Text(
            "Basic text with multiple lines ahshsh ahashsahas sahsadhashd ádhash dáhdhsahhdahdashdhasdhas háhhasd this is line 4",
            color = Color.Red,
            style = TextStyle(
                fontSize = 22.sp, fontWeight = FontWeight.Bold
            ),
            // underline or line through
            textDecoration = TextDecoration.None,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp),
            lineHeight = 20.sp,
            letterSpacing = 5.sp
        )
    }

    @Composable
    private fun TextFieldExample() {
        var textValue by remember { mutableStateOf("") }

        TextField(value = textValue, onValueChange = {
            textValue = it
        }, modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(), leadingIcon = {
            Icon(imageVector = Icons.Default.Email, contentDescription = null)
        }, placeholder = {
            Text("Enter your email...")
        })
        // password
        // Oh password should not be visible -> done
        var password by remember { mutableStateOf("") }
        TextField(value = password, onValueChange = {
            password = it
        }, modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(), leadingIcon = {
            Icon(imageVector = Icons.Default.Lock, contentDescription = null)
        }, placeholder = {
            Text("Enter your password")
        }, visualTransformation = PasswordVisualTransformation())

        // outline
        var userName  by remember {
            mutableStateOf("")
        }
        OutlinedTextField(userName,{
            userName = it
        }, modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth())

        // default text field allow fully customize
        // so we should use BasicTextField if we want to customize text field layout
        var defaultValue by remember {
            mutableStateOf("")
        }
        BasicTextField(value = defaultValue, onValueChange = {
            defaultValue = it
        }, modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .border(1.dp,Color.Gray, RoundedCornerShape(10.dp)), decorationBox = { innerTextField ->
                Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 10.dp)) {
                    Image(imageVector = Icons.Default.Call, contentDescription = null)
                    innerTextField()
                }
        })

    }
}