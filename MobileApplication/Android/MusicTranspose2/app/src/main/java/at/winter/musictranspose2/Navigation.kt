package at.winter.musictranspose2

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.winter.musictranspose2.views.login.*

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route){
        composable(route = Screen.LoginScreen.route){
            LoginScreen({navController.navigate(Screen.MainScreen.route)},{})
        }
        composable(route = Screen.MainScreen.route){
            MainScreen()
        }

    }
}