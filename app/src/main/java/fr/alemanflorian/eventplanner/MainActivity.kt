package fr.alemanflorian.eventplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.core.view.WindowCompat
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.alemanflorian.eventplanner.ui.theme.AppColors

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
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
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = AppColors.main) {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "login") {
                    composable("login") { LoginWidget(navController) }
                }
            }
        }
    }
}