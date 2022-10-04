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
import fr.alemanflorian.eventplanner.user.AppUser
import fr.alemanflorian.eventplanner.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = AppColors.main) {

            }
        }

        GlobalScope.launch (Dispatchers.IO){
            AppUser.load()
            withContext(Dispatchers.Main)
            {
                setContent {
                    Surface(modifier = Modifier.fillMaxSize(), color = AppColors.main) {
                        val navController = rememberNavController()
                        NavHost(navController, startDestination = "login") {
                            composable("login") { LoginWidget().build(navController) }
                        }
                    }
                }
            }
        }


    }
}