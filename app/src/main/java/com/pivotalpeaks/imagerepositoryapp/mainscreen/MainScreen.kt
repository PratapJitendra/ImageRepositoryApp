package com.pivotalpeaks.imagerepositoryapp.mainscreen

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets
import com.pivotalpeaks.imagerepositoryapp.detailsscreen.DetailsViewModel
import com.pivotalpeaks.imagerepositoryapp.detailsscreen.ImageDetails
import com.pivotalpeaks.imagerepositoryapp.screens.Screens


@Composable
fun MainScreen(){
    val navController= rememberNavController()
    val context= LocalContext.current

    ProvideWindowInsets {
        NavHost(navController = navController, startDestination = NavScreen.Home.route ){

            composable(NavScreen.Home.route){
                val viewModel= hiltViewModel<MainViewModel>()
                Screens(viewModel = viewModel, selectImage = {
                    navController.navigate("${NavScreen.ImageDetails.route}/$it")
                })
                viewModel.toast.observe(LocalLifecycleOwner.current){
                    Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "MainScreen: "+it.toString() )
                }
            }
            composable(route = NavScreen.ImageDetails.routeWithArg,
            arguments = listOf(navArgument(NavScreen.ImageDetails.argument)
            {type= NavType.LongType})){ backStackEntry ->
                val viewModel= hiltViewModel<DetailsViewModel>()
                val imageId=backStackEntry.arguments?.getLong(NavScreen.ImageDetails.argument)?: return@composable
                viewModel.getImage(imageId)
                ImageDetails(viewModel = viewModel) {
                    navController.navigateUp()
                }

            }
        }
    }
}

sealed class NavScreen(val route:String){

    object Home:NavScreen("Home")
    object ImageDetails:NavScreen("ImageDetails"){
        const val routeWithArg:String="ImageDetails/{imageId}"
        const val argument:String="imageId"
    }

}