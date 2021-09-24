package com.pivotalpeaks.drawerapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

enum class DrawerAppScene{Scene1,Scene2,Scene3}

@Composable
fun DrawerComponent(){
    val drawerState= rememberDrawerState(DrawerValue.Closed)

    val currentScreen= remember {
        mutableStateOf(DrawerAppScene.Scene1)
    }
    val coroutineScope= rememberCoroutineScope()
    ModalDrawer(drawerState=drawerState, drawerContent ={DrawerContentComponent(
        currentScreen=currentScreen,
        closeDrawer = {coroutineScope.launch { drawerState.close() } }
    )},
    content = {BodyContent(currentScreen=currentScreen.value,
    openDrawer = {coroutineScope.launch { drawerState.open() }})})
}

@Composable
fun BodyContent(currentScreen: DrawerAppScene,
openDrawer:()->Unit) {
    when(currentScreen){
        DrawerAppScene.Scene1->Screen1Component(openDrawer)
        DrawerAppScene.Scene2->Screen2Component(openDrawer)
        DrawerAppScene.Scene3->Screen3Component(openDrawer)
    }
}

@Composable
fun Screen3Component(openDrawer: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text(text = "Title Secene 3")},
            navigationIcon = { IconButton(onClick = openDrawer) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription ="Menu" )
            }})

        Surface(color = Color(0XFFff7d7d.toInt()),
            modifier = Modifier.weight(1f)) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = { Text(text = "Scene3")})

        }

    }
}

@Composable
fun Screen2Component(openDrawer: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text(text = "Title Secene 2")},
            navigationIcon = { IconButton(onClick = openDrawer) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription ="Menu" )
            }})

        Surface(color = Color(0XFFff7d7d.toInt()),
            modifier = Modifier.weight(1f)) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = { Text(text = "Scene2")})

        }

    }
}

@Composable
fun Screen1Component(openDrawer: () -> Unit) {

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text(text = "Title Secene 1")},
        navigationIcon = { IconButton(onClick = openDrawer) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription ="Menu" )
        }})

        Surface(color = Color(0XFFff7d7d.toInt()),
        modifier = Modifier.weight(1f)) {
            Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = { Text(text = "Scene1")})

        }

    }
}

@Composable
fun DrawerContentComponent(currentScreen:MutableState<DrawerAppScene>,
closeDrawer:()->Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        for (index in DrawerAppScene.values().indices){
            val screen=getScreenByIndex(index)
            Column(Modifier.clickable(onClick = { currentScreen.value = screen
            closeDrawer()
            }), content = {
                Surface(modifier = Modifier.fillMaxWidth(),
                    color = if (currentScreen.value==screen) {
                        MaterialTheme.colors.secondary
                    } else
                    {
                        MaterialTheme.colors.surface
                    }) {
                    Text(text = screen.name,modifier = Modifier.padding(16.dp))

                }
            })
        }

    }
}

fun getScreenByIndex(index: Int)= when(index) {
    0 -> DrawerAppScene.Scene1
    1->DrawerAppScene.Scene2
    2->DrawerAppScene.Scene3
    else->DrawerAppScene.Scene1
}
