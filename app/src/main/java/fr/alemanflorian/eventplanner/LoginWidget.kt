package fr.alemanflorian.eventplanner

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.alemanflorian.eventplanner.ui.theme.AppColors
import fr.alemanflorian.eventplanner.user.AppUser

class LoginWidget
{
    @Composable
    fun build(navController: NavController?)
    {
        println("LOGIN")
        Text(
            text = "HELLO ${AppUser.get().name}",
            color = AppColors.app,
            fontSize = 60.sp
        )
    }
}

@Preview
@Composable
fun ComposablePreview()
{
    LoginWidget().build(null)
}