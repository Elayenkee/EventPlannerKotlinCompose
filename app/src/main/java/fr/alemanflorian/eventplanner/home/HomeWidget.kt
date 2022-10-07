package fr.alemanflorian.eventplanner.home

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarViewMonth
import androidx.compose.material.icons.outlined.CalendarViewWeek
import androidx.compose.material.icons.outlined.EditCalendar
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
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.alemanflorian.eventplanner.R
import fr.alemanflorian.eventplanner.components.screenCreate
import fr.alemanflorian.eventplanner.event.Event
import fr.alemanflorian.eventplanner.team.Team
import fr.alemanflorian.eventplanner.ui.theme.AppColors
import fr.alemanflorian.eventplanner.user.AppUser
import fr.alemanflorian.eventplanner.user.AppUserViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
    println("HomeWidget.Content")
    var eventsLiveData by remember { mutableStateOf(HomeViewModel()) }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    )
    {
        println("${eventsLiveData.events.value.size}")
        eventsLiveData.events.value.forEach {
            itemEvent(event = it)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun itemEvent(event: Event)
{
    val cornerRadius = 8.dp
    var team by remember { mutableStateOf(TeamViewModel(event.uidTeam)) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(cornerRadius),
        backgroundColor = AppColors.second
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.event),
                    contentDescription = "iconCalendar",
                    tint = AppColors.app,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "   TODO 2022-09-30 19:20",
                    color = AppColors.app
                )
                if(event.uidTeam != null && team.team.value != null)
                {
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))
                    Text(
                        text = team.team.value!!.name,
                        color = AppColors.bottomButton,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.group),
                        contentDescription = "iconGroup",
                        tint = AppColors.bottomButton,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
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

class HomeViewModel: ViewModel()
{
    private var teamEvents: MutableMap<String, Event> = mutableMapOf()
    private var userEvents: MutableMap<String, Event> = mutableMapOf()
    val events: MutableState<List<Event>> = mutableStateOf(mutableListOf())

    init
    {
        val user = AppUser.get()
        Firebase.firestore.collection("events").whereIn("uidTeam", user.teams).addSnapshotListener{
                snapshot, exception ->
            run {
                snapshot.let {
                    teamEvents = mutableMapOf()
                    snapshot!!.documents.forEach { teamEvents[it.id] = Event(it.id, it.data as (Map<String, Any>))}
                    emitEvents()
                }
            }
        }
        Firebase.firestore.collection("events").whereIn(FieldPath.documentId(), user.events).addSnapshotListener{
                snapshot, exception ->
            run {
                snapshot.let {
                    userEvents = mutableMapOf()
                    snapshot!!.documents.forEach { userEvents[it.id] = Event(it.id, it.data as (Map<String, Any>))}
                    emitEvents()
                }
            }
        }
    }

    private fun emitEvents()
    {
        userEvents = userEvents.filter { !teamEvents.containsKey(it.key) } as MutableMap<String, Event>
        println(teamEvents)
        println(userEvents)

        val all = mutableListOf<Event>()
        all.addAll(teamEvents.values)
        all.addAll(userEvents.values)
        //TODO SORT
        events.value = all
    }
}

class TeamViewModel(uid: String?): ViewModel()
{
    val team: MutableState<Team?> = mutableStateOf(null)

    init {
        if(uid != null)
        {
            viewModelScope.launch {
                val docTeam = Firebase.firestore.collection("teams").document(uid)
                val doc = docTeam.get().await()
                team.value = Team(doc.id, doc.data as (Map<String, Any>))
            }
        }
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