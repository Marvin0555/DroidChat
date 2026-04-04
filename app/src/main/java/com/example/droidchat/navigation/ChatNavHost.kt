package com.example.droidchat.navigation

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.droidchat.navigation.extension.SlideInTo
import com.example.droidchat.navigation.extension.SlideOutTo
import com.example.droidchat.ui.feature.signin.SignInRoute
import com.example.droidchat.ui.feature.signup.SignUpRoute
import com.example.droidchat.ui.feature.splash.SplashRoute
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    object SplashRoute

    @Serializable
    object SignInRoute

    @Serializable
    object SignUpRoute
}
@Composable
fun ChatNavHost() {
    val navController = rememberNavController()
    val activity = LocalContext.current as? Activity

    NavHost(
        navController = navController,
        startDestination = Route.SplashRoute,
    ){
        composable<Route.SplashRoute>(
        ) {
            SplashRoute(
                onNavigateToSignIn = {
                    navController.navigate(
                        route = Route.SignInRoute,
                        navOptions = navOptions{
                            popUpTo(Route.SplashRoute){
                                inclusive = true
                            }
                        }
                    )
                },
                onNavigateToMain = {
                    Toast.makeText(navController.context, "Navigate to main", Toast.LENGTH_SHORT).show()
                },
                onCloseApp = {
                    activity?.finish()
                }
            )

        }
        composable<Route.SignInRoute>(
            enterTransition = {
                this.SlideInTo(AnimatedContentTransitionScope.SlideDirection.Right)
            },
            exitTransition = {
                this.SlideOutTo(AnimatedContentTransitionScope.SlideDirection.Left)
            }
        ) {
            val context = LocalContext.current
            SignInRoute(
                navigateToSignUp = {
                    navController.navigate(Route.SignUpRoute)
                },
                navigateToMain = {
                    Toast.makeText(context, "Navigate to main", Toast.LENGTH_SHORT).show()
                }
            )
        }
        composable<Route.SignUpRoute>(
            enterTransition = {
                this.SlideInTo(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                this.SlideOutTo(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            SignUpRoute(
                onSignUpSuccess = {
                    navController.popBackStack()
                }
            )

        }
    }
}