package at.winter.musictranspose2.views.login

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import at.winter.musictranspose2.Navigation
import at.winter.musictranspose2.R
import at.winter.musictranspose2.ui.theme.MusicTranspose2Theme
import kotlinx.coroutines.delay

val tag = "LoginView"

enum class LoginState {
    LOGIN, REGISTER
}

@Composable
fun LoginScreen(
    onLogin: () -> kotlin.Unit,
    onRegister: () -> kotlin.Unit,
    modifier: Modifier = Modifier
) {
    var username by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordRepeated by remember {
        mutableStateOf("")
    }

    //Animation
    val density = LocalDensity.current
    val activity = (LocalContext.current as? Activity)
    var loginVisible by remember { mutableStateOf(true) }
    var registerVisible by remember { mutableStateOf(false) }
    var exit by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(key1 = exit) {
        if (exit) {
            delay(2000)
            exit = false
        }
    }

    BackHandler(enabled = true) {
        if (!loginVisible && registerVisible) {
            loginVisible = true
            registerVisible = false
        } else {
            if (exit) {
                activity?.finish()
            } else {
                exit = true
                Toast.makeText(context, "To Leave the application please press back again!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = loginVisible,
            enter = slideInHorizontally {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + expandHorizontally(
                expandFrom = Alignment.End
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutHorizontally() + shrinkHorizontally() + fadeOut()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    LoginTitle(
                        loginState = LoginState.LOGIN,
                        modifier = modifier
                    )
                    LoginForm(
                        username = username,
                        onUsernameChange = { username = it },
                        password = password,
                        onPasswordChange = { password = it },
                        onLogin = { onLogin() },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .align(alignment = Alignment.BottomCenter)
                        .padding(bottom = 32.dp)
                ) {
                    Text(
                        text = "Don't have an account? ",
                        fontSize = 14.sp,
                        modifier = modifier.padding(bottom = 12.dp)
                    )
                    Text(
                        text = "Sign up",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W800,
                        modifier = modifier
                            .padding(bottom = 12.dp)
                            .clickable {
                                loginVisible = false
                                registerVisible = true
                            }
                    )
                }
            }


        }

        AnimatedVisibility(
            visible = registerVisible,
            enter = slideInHorizontally {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + expandHorizontally(
                // Expand from the top.
                expandFrom = Alignment.End
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutHorizontally() + shrinkHorizontally() + fadeOut()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                LoginTitle(
                    loginState = LoginState.REGISTER,
                    modifier = modifier
                )

//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Text(
//                        text = """
//                            Please inform your organization
//                            admin if you registered successfully!
//                        """.trimIndent(),
//                        fontSize = 20.sp,
//                        modifier = modifier.padding(bottom = 12.dp)
//                    )
//                }

                RegistrationForm(
                    username = username,
                    onUsernameChange = { username = it },
                    email = email,
                    onEmailChange = { email = it },
                    password = password,
                    onPasswordChange = { password = it },
                    passwordRepeated = passwordRepeated,
                    onPasswordRepeatedChange = { passwordRepeated = it },
                    onBack = { registerVisible = false; loginVisible = true },
                    onRegister = { onRegister() },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun LoginTitle(loginState: LoginState, modifier: Modifier) {
    Image(
        painter = painterResource(id = R.mipmap.musictranspose_icon),
        contentDescription = "Login image",
        Modifier
            .size(180.dp)
            .padding(bottom = 16.dp)
    )

    if (loginState == LoginState.LOGIN) {
        Text(
            text = "Welcome to,",
            fontSize = 32.sp,
            fontWeight = FontWeight.W800,
            modifier = modifier
        )
        Text(
            text = "MusicTranspose!",
            fontSize = 32.sp,
            fontWeight = FontWeight.W800,
            modifier = modifier.padding(bottom = 12.dp)
        )
    } else {
        Text(
            text = "Registration",
            fontSize = 32.sp,
            fontWeight = FontWeight.W800,
            modifier = modifier
        )
    }
}


@Composable
fun DoubleButton(
    textBt1: String, textBt2: String, onBt1: () -> Unit, onBt2: () -> Unit, modifier: Modifier
) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.fillMaxWidth()) {
        Button(
            onClick = { onBt1() },
            modifier = Modifier.width(140.dp)
        ) {
            Text(
                text = textBt1
            )
        }
        Button(
            onClick = { onBt2() },
            modifier = Modifier.width(140.dp)
        ) {
            Text(
                text = textBt2
            )
        }
    }
}

@Composable
fun LoginForm(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> kotlin.Unit,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { onUsernameChange(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = modifier.padding(top = 32.dp),
            label = {
                Text(
                    "Username"
                )
            })
        OutlinedTextField(
            value = password,
            onValueChange = { onPasswordChange(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = modifier.padding(bottom = 32.dp),
            label = {
                Text(
                    "Password"
                )
            },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = { onLogin() },
            modifier = Modifier.width(140.dp)
        ) {
            Text(
                text = "Login"
            )
        }
    }
}

@Composable
fun RegistrationForm(
    username: String,
    onUsernameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordRepeated: String,
    onPasswordRepeatedChange: (String) -> Unit,
    onBack: () -> kotlin.Unit,
    onRegister: () -> Unit,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { onUsernameChange(it) },
            singleLine = true,

            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = modifier
                .padding(top = 16.dp)
                .padding(bottom = 8.dp),
            label = {
                Text(
                    "Username"
                )
            })
        OutlinedTextField(
            value = email,
            onValueChange = { onEmailChange(it) },
            modifier = modifier.padding(bottom = 8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            label = {
                Text(
                    "Email"
                )
            })
        OutlinedTextField(
            value = password,
            onValueChange = { onPasswordChange(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            modifier = modifier.padding(bottom = 8.dp),
            label = {
                Text(
                    "Password"
                )
            },
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            value = passwordRepeated,
            onValueChange = { onPasswordRepeatedChange(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = modifier.padding(bottom = 32.dp),
            label = {
                Text(
                    "Repeat Password"
                )
            },
            visualTransformation = PasswordVisualTransformation()
        )
        DoubleButton(
            textBt1 = "Back",
            textBt2 = "Register",
            onBt1 = { onBack() },
            onBt2 = { onRegister() },
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Login() {
    MusicTranspose2Theme {
        LoginScreen({}, {})
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginForm() {
//    MusicTranspose2Theme {
//        LoginForm(onBack = {}, onLogin = {}, modifier = Modifier)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun RegistrationForm() {
//    MusicTranspose2Theme {
//        RegistrationForm(
//            onBack = {},
//            onRegister = {},
//            modifier = Modifier
//        )
//    }
//}