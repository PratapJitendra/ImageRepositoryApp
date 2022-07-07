package com.pivotalpeaks.imagerepositoryapp.screens

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.navigationBarsPadding
import com.pivotalpeaks.imagerepositoryapp.Image
import com.pivotalpeaks.imagerepositoryapp.R
import com.pivotalpeaks.imagerepositoryapp.extensions.visible
import com.pivotalpeaks.imagerepositoryapp.mainscreen.MainViewModel
import com.pivotalpeaks.imagerepositoryapp.ui.theme.Purple200


@Composable
fun Screens(
    viewModel: MainViewModel,
    selectImage: (Long) -> Unit
) {
    val images: List<Image> by viewModel.imageList.observeAsState(listOf())
    val selectedTab = ImageTab.getTabsFromRes(viewModel.selectTab.value)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(false)
    val tabs = ImageTab.values()

    ConstraintLayout {
        val (body, progres) = createRefs()

        Scaffold(backgroundColor = MaterialTheme.colors.primarySurface,
            topBar = { Appbar() },
            modifier = Modifier.constrainAs(body) {
                top.linkTo(parent.top)
            },
            bottomBar = {
                BottomNavigation(
                    backgroundColor = Purple200,
                    modifier = Modifier.navigationBarsHeight(56.dp)
                ) {
                    tabs.forEach { tab ->
                        BottomNavigationItem(
                            selected = tab == selectedTab,
                            onClick = { viewModel.selectTab(tab.title) },
                            icon = { Icon(imageVector = tab.icon, contentDescription = null) },
                            label = {
                                Text(
                                    text = stringResource(id = tab.title),
                                    color = Color.White
                                )
                            },
                            selectedContentColor = LocalContentColor.current,
                            unselectedContentColor = LocalContentColor.current,
                            modifier = Modifier.navigationBarsPadding()
                        )
                    }

                }
            }) { innerPadding ->
            val modifier=Modifier.padding(innerPadding)
            Crossfade(targetState = selectedTab) { destination ->
                when (destination) {
                    ImageTab.Home -> DisplayImages(images, selectImage,modifier)
                    ImageTab.List -> ListImages(images , selectImage ,modifier )
                }


            }

        }

        CircularProgressIndicator(
            modifier = Modifier
                .constrainAs(progres) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .visible(isLoading)


        )


    }
}

@Composable
fun Appbar() {
    TopAppBar(
        elevation = 6.dp,
        backgroundColor = Purple200,
        modifier = Modifier.height(45.dp)
    ) {

        Text(
            text = stringResource(id = R.string.app_name),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically)
        )

    }
}

enum class ImageTab(
    @StringRes val title: Int,
    val icon: ImageVector
) {

    Home(R.string.menu_home, Icons.Filled.Home),
    List(R.string.menu_list, Icons.Filled.List);

    companion object {
        fun getTabsFromRes(@StringRes resource: Int): ImageTab {
            return when (resource) {
                R.string.menu_list -> List
                else -> Home
            }
        }
    }
}