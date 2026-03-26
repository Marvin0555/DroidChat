package com.example.droidchat.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.example.droidchat.ui.feature.signin.SignInRoute
import com.example.droidchat.ui.feature.splash.SplashRoute
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

@Serializable
object SplashRoute

@Serializable
object SignInRoute

@Serializable
object SignUpRoute


@Composable
fun ChatNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SplashRoute
    ){
        composable<SplashRoute> {
            SplashRoute(
                onNavigateToSignIn = {
                    navController.navigate(
                        route = SignInRoute,
                        navOptions = navOptions{
                            popUpTo(SplashRoute){
                                inclusive = true
                            }
                        }
                    )
                }
            )

        }
        composable<SignInRoute> {
            SignInRoute()
        }
        composable<SignUpRoute> {

        }
    }
}