package fr.alemanflorian.eventplanner.home

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.alemanflorian.eventplanner.R
import fr.alemanflorian.eventplanner.components.screenCreate
import fr.alemanflorian.eventplanner.ui.theme.AppColors
import fr.alemanflorian.eventplanner.user.AppUserViewModel

@Composable
fun home(navController: NavController?, viewModel: AppUserViewModel)
{
    println("${System.currentTimeMillis()} : HomeWidget.build ${viewModel.user.value}")
    screenCreate(
        child = {
            content()
        },
        bottom = {
            bottomBar()
        }
    )
}

@Composable
fun content()
{

}

@Composable
fun bottomBar()
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(112.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)){
            
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(color = AppColors.second)
                .padding(horizontal = 8.dp, vertical = 5.dp)
        ) {
            bottomButton(R.drawable.group) {

            }
            Spacer(modifier = Modifier.width(8.dp))
            bottomButton(R.drawable.event) {

            }
        }
    }
}

@Composable
fun bottomButton(@DrawableRes image:  Int, onClick: ()->Unit)
{
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()
    val radius = if (isPressed.value) 12.dp else 23.dp
    val cornerRadius = animateDpAsState(targetValue = radius)
    val color = animateColorAsState(targetValue = if (isPressed.value) AppColors.bottomButton else AppColors.main)
    val imageColor = animateColorAsState(targetValue = if (isPressed.value) Color.White else AppColors.bottomButton)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(cornerRadius.value))
            .background(color = color.value)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple()
            )
            { }
    )
    {
        Image(
            painter = painterResource(id = image),
            contentDescription = "buttonImage",
            colorFilter = ColorFilter.tint(imageColor.value),
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ComposablePreview()
{
    Surface(modifier = Modifier.fillMaxSize(), color = AppColors.main) {
        home(null, AppUserViewModel())
    }
}