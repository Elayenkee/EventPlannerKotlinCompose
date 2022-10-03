package fr.alemanflorian.eventplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.alemanflorian.eventplanner.ui.theme.EventPlannerTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventPlannerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting("Android 2")
                }
            }
        }

        /*FirebaseAuth.getInstance().signInWithEmailAndPassword("florian.aleman@gmail.com", "123456")
            .addOnSuccessListener{
                println("OK >> $it")
            }
            .addOnFailureListener {
                println("FAIL >> $it")
            }*/
        println("OUI")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EventPlannerTheme {
        Greeting("Android")
    }
}