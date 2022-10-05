package fr.alemanflorian.eventplanner.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.alemanflorian.eventplanner.ui.theme.AppColors
import fr.alemanflorian.eventplanner.user.AppUser
import fr.alemanflorian.eventplanner.user.AppUserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun login(navController: NavController?, viewModel: AppUserViewModel, modifier:Modifier = Modifier)
{
    println("${System.currentTimeMillis()} : LoginWidget.build ${viewModel.user.value}")
    var pseudo by remember { mutableStateOf("Matof") }
    var errorPseudo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("matoflink@gmail.com") }
    var errorEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("123456") }
    var confirmPassword by remember { mutableStateOf("123456") }
    var errorConfirmPassword by remember { mutableStateOf("") }

    var isInscription by remember {mutableStateOf(true)}
    var passwordVisible  by remember {mutableStateOf(false)}
    var confirmPasswordVisible by remember {mutableStateOf(false)}

    if(viewModel.loading.value)
    {
        Text(text = "Loading", color = Color.White)
        return
    }

    if(viewModel.user.value != null)
    {
        navController?.navigate("home"){
            launchSingleTop = true
        }
        return
    }

    val focusManager = LocalFocusManager.current
    Box (
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ){
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(15.dp),
            modifier = modifier
                .width(350.dp)
                .wrapContentHeight()
        ) {
            val listState = rememberLazyListState()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.aligned(Alignment.CenterVertically),
                modifier = modifier
                    .padding(35.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // TITLE
                Text(
                    text = if (isInscription) "Inscription" else "Connexion",
                    color = AppColors.app,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(modifier = modifier.height(10.dp))

                // NAME
                if (isInscription)
                {
                    OutlinedTextField(
                        value = pseudo,
                        singleLine = true,
                        isError = errorPseudo.length > 0,
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = AppColors.app,
                            backgroundColor = AppColors.textFieldBackground,
                            focusedBorderColor = AppColors.app,
                            unfocusedBorderColor = Color.Transparent),
                        label = {
                            Row(modifier = modifier.fillMaxWidth()) {
                                Icon(
                                    Icons.Outlined.AccountBox,
                                    contentDescription = "iconPseudo",
                                    tint = AppColors.app
                                )
                                Spacer(modifier = modifier.width(10.dp))
                                Text(
                                    text = "Pseudo",
                                    color = AppColors.app
                                )
                            }
                        },
                        onValueChange = {
                            pseudo = it
                            if(pseudo.length <= 3)
                                errorPseudo = "Choisir un pseudo avec plus de 3 caractères"
                            else
                                errorPseudo = ""
                        }
                    )
                    if(errorPseudo.length > 0)
                        Text(
                            text = errorPseudo,
                            fontSize = 10.sp,
                            color = Color.Red
                        )
                    Box(modifier = modifier.height(5.dp))
                }

                // EMAIL
                OutlinedTextField(
                    value = email,
                    isError = errorEmail.length > 0,
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = AppColors.app,
                        backgroundColor = AppColors.textFieldBackground,
                        focusedBorderColor = AppColors.app,
                        unfocusedBorderColor = Color.Transparent),
                    label = {
                        Row(modifier = modifier.fillMaxWidth()) {
                            Icon(
                                Icons.Outlined.MailOutline,
                                contentDescription = "iconMail",
                                tint = AppColors.app
                            )
                            Spacer(modifier = modifier.width(10.dp))
                            Text(
                                text = "Email",
                                color = AppColors.app
                            )
                        }
                    },
                    singleLine = true,
                    onValueChange = {
                        email = it
                        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                            errorEmail = "L'email n'est pas valide"
                        else
                            errorEmail = ""
                    }
                )
                if(errorEmail.length > 0)
                    Text(
                        text = errorEmail,
                        fontSize = 10.sp,
                        color = Color.Red
                    )
                Box(modifier = modifier.height(5.dp))

                // PASSWORD
                OutlinedTextField(
                    value = password,
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    visualTransformation = if(confirmPasswordVisible)  VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = AppColors.app,
                        backgroundColor = AppColors.textFieldBackground,
                        focusedBorderColor = AppColors.app,
                        unfocusedBorderColor = Color.Transparent),
                    label = {
                        Row(modifier = modifier.fillMaxWidth()) {
                            Icon(
                                Icons.Outlined.Lock,
                                contentDescription = "iconPassword",
                                tint = AppColors.app
                            )
                            Spacer(modifier = modifier.width(10.dp))
                            Text(
                                text = "Password",
                                color = AppColors.app
                            )
                        }
                    },
                    trailingIcon = {
                        val image = if (passwordVisible)Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                        // Please provide localized description for accessibility services
                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = {passwordVisible = !passwordVisible}){
                            Icon(imageVector  = image, description, tint = AppColors.app)
                        }
                    },
                    onValueChange = {
                        password = it
                    }
                )
                Box(modifier = modifier.height(5.dp))

                // CONFIRM PASSWORD
                if (isInscription) {OutlinedTextField(
                    value = confirmPassword,
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    isError = errorConfirmPassword.length > 0,
                    visualTransformation = if(confirmPasswordVisible)  VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = AppColors.app,
                        backgroundColor = AppColors.textFieldBackground,
                        focusedBorderColor = AppColors.app,
                        unfocusedBorderColor = Color.Transparent),
                    label = {
                        Row(modifier = modifier.fillMaxWidth()) {
                            Icon(
                                Icons.Outlined.Lock,
                                contentDescription = "iconConfirmPassword",
                                tint = AppColors.app
                            )
                            Spacer(modifier = modifier.width(10.dp))
                            Text(
                                text = "Confirmer Password",
                                color = AppColors.app
                            )
                        }
                    },
                    trailingIcon = {
                        val image = if (confirmPasswordVisible)Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                        // Please provide localized description for accessibility services
                        val description = if (confirmPasswordVisible) "Hide password" else "Show password"

                        IconButton(onClick = {confirmPasswordVisible = !confirmPasswordVisible}){
                            Icon(imageVector  = image, description, tint = AppColors.app)
                        }
                    },
                    onValueChange = {
                        confirmPassword = it
                        if(password == confirmPassword)
                            errorConfirmPassword = ""
                        else
                            errorConfirmPassword = "Les mots de passe doivent être identiques"
                    }
                )
                    if(errorEmail.length > 0)
                        Text(
                            text = errorConfirmPassword,
                            fontSize = 10.sp,
                            color = Color.Red
                        )
                }
                Box(modifier = modifier.height(10.dp))

                // BOUTON VALIDER
                Button(
                    onClick = {
                        if(errorEmail.length > 0 || errorPseudo.length > 0 || errorConfirmPassword.length > 0)
                            return@Button

                        validate(isInscription, pseudo, email, password, viewModel)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.app),
                    shape = RoundedCornerShape(10.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = if(!isInscription) "Se connecter" else "S'inscrire",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(modifier = modifier.height(20.dp))

                // CHANGER
                TextButton(onClick = {
                    isInscription = !isInscription
                }) {
                    Text(
                        text = if(isInscription) "Se connecter" else "S'inscrire",
                        color = AppColors.app
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ComposablePreview()
{
    Surface(modifier = Modifier.fillMaxSize(), color = AppColors.main) {
        login(null, AppUserViewModel())
    }
}

fun validate(isInscription:Boolean, name:String, mail:String, password:String, appUserViewModel: AppUserViewModel)
{
    GlobalScope.launch(Dispatchers.IO) {

        try {
            if(isInscription)
            {
                val userCredential = Firebase.auth.createUserWithEmailAndPassword(mail, password).await()
                val uid = userCredential.user!!.uid
                val docUser = Firebase.firestore.collection("users").document(uid)
                val map = mapOf("name" to name)
                docUser.set(map).await()
                val user = AppUser(uid, map)
                AppUser.set(user)
                appUserViewModel.setValue(user)
            }
            else
            {
                val userCredential = Firebase.auth.signInWithEmailAndPassword(mail, password).await()
                val uid = userCredential.user!!.uid
                val docUser = Firebase.firestore.collection("users").document(uid)
                val user = AppUser(uid, docUser.get().await().data as (Map<String, Any>))
                AppUser.set(user)
                appUserViewModel.setValue(user)
            }
        }
        catch (e: FirebaseAuthException)
        {
            println(">>>>>>>>>>>>>>> ${e.errorCode}")
        }
    }
}