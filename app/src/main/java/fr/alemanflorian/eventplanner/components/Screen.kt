package fr.alemanflorian.eventplanner.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.alemanflorian.eventplanner.ui.theme.AppColors
import fr.alemanflorian.eventplanner.user.AppUser

@Composable
fun screenCreate(child: (@Composable() () -> Unit)? = null, bottom: (@Composable() () -> Unit)? = null)
{
    Scaffold(
        backgroundColor = AppColors.third
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                NavigationBar()
                Box(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f))
                {
                    if (child != null)
                    {
                        child
                    }
                }
                if (bottom != null)
                {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )
                }
            }
            if(bottom != null)
            {
                Box(modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .fillMaxWidth()
                )
                {
                    bottom()
                }
            }
        }
    }
}

@Composable
fun NavigationBar()
{
    TopAppBar(
        title = {},
        actions = {
            Button(
                modifier = Modifier.fillMaxHeight(),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.main),
            ) {
                Row() {
                    Text(
                        AppUser.get().name,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        Icons.Outlined.AccountBox,
                        contentDescription = "iconTopAppbar",
                        tint = Color.White
                    )
                }
            }
        },
        backgroundColor = AppColors.main
    )
}

@Preview(showSystemUi = true)
@Composable
fun ComposablePreview()
{
    screenCreate(
        bottom = {
            Text(
                text = "tedqsd",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Red
                )
            )
        }
    )
}