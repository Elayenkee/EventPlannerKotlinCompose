package fr.alemanflorian.eventplanner.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import fr.alemanflorian.eventplanner.ui.theme.AppColors
import fr.alemanflorian.eventplanner.user.AppUserViewModel

@Composable
fun home(navController: NavController?, viewModel: AppUserViewModel)
{
    println("${System.currentTimeMillis()} : HomeWidget.build ${viewModel.user.value}")
    Text(text = "Home ${viewModel.user.value}", color = Color.White)
}

@Preview(showSystemUi = true)
@Composable
fun ComposablePreview()
{
    Surface(modifier = Modifier.fillMaxSize(), color = AppColors.main) {
        home(null, AppUserViewModel())
    }
}