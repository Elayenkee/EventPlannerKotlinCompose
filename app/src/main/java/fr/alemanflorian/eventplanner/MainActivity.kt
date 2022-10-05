package fr.alemanflorian.eventplanner

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.alemanflorian.eventplanner.home.home
import fr.alemanflorian.eventplanner.login.login
import fr.alemanflorian.eventplanner.ui.theme.AppColors
import fr.alemanflorian.eventplanner.user.AppUserViewModel
import fr.alemanflorian.eventplanner.utils.Utils

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        Utils.context = applicationContext;
        super.onCreate(savedInstanceState)
        /*setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = AppColors.main) {
                Greeting("Android 2")
            }
        }

        /*FirebaseAuth.getInstance().signInWithEmailAndPassword("florian.aleman@gmail.com", "123456")
            .addOnSuccessListener{
                println("OK >> $it")
            }
            .addOnFailureListener {
                println("FAIL >> $it")
            }*/*/
        //WindowCompat.setDecorFitsSystemWindows(window, false)

        /*GlobalScope.launch(Dispatchers.IO) {
            AppUser.load()
        }*/

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        val viewModel = AppUserViewModel()

        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = AppColors.main) {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "login") {
                    composable("login") { login(navController, viewModel) }
                    composable("home") { home(navController, viewModel) }
                }
            }
        }
    }
}