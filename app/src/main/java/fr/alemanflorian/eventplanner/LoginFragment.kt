package fr.alemanflorian.eventplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController

class LoginFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? 
    {
        return ComposeView(requireContext()).apply { 
            setContent { 
                Text(text = "HELLO")
            }
        }
    }
}

@Composable
fun LoginWidget(navController: NavController)
{
    Text(text = "HELLO")
}