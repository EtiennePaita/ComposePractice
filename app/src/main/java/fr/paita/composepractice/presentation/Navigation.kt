package fr.paita.composepractice.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.paita.composepractice.presentation.screens.home.HomeScreen
import fr.paita.composepractice.presentation.screens.playground.PlaygroundScreen


sealed class Screen(val route: String, val name: String) {

    object Home : Screen("home", "Compose practice")

    object Playground : Screen("playground", "Playground")

    object Profile : Screen("profile/{userId}", "Profil") {
        fun withArgs(userId: String): String {
            return "profile/$userId"
        }
    }

}

val screens = listOf(
    Screen.Home,
    Screen.Playground,
    Screen.Profile
)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val canNavigateBack = navController.previousBackStackEntry != null

    // Top bar title
    val currentScreen = screens.find { screen ->
        currentDestination?.route?.contains(screen.route) == true
    }
    val currentScreenName = currentScreen?.name ?: Screen.Home.name

    TopAppBar(
        title = {
            Text(currentScreenName)
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Retour"
                    )
                }
            }
        }
    )
}


@Composable
fun BottomBar(navController: NavHostController) {
    BottomAppBar {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Bottom app bar",
        )
    }
}




@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(navController = navController)
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.Playground.route) {
                    //popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }) {
                Icon(
                    Icons.Filled.Build,
                    contentDescription = "Playground screen call to action"
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {

            NavHost(
                navController = navController,
                startDestination = Screen.Home.route
            ) {
                composable(route = Screen.Home.route) {
                    HomeScreen()
                }
                composable(route = Screen.Playground.route) {
                    PlaygroundScreen()
                }
                composable(
                    route = Screen.Profile.route,
                    arguments = listOf(navArgument("userId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")
                    //ProfileScreen(navController = navController, userId = userId)
                }

            }

        }
    }


}
