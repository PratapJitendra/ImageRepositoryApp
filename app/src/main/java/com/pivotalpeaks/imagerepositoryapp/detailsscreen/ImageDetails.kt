package com.pivotalpeaks.imagerepositoryapp.detailsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.pivotalpeaks.imagerepositoryapp.Image
import com.pivotalpeaks.imagerepositoryapp.util.NetworkImage

@Composable
fun ImageDetails(
    viewModel: DetailsViewModel,
    pressOnBack: () -> Unit
){
    val details:Image? by viewModel.imageDetails.observeAsState()

    details?.let { image ->
        ImageDetailsBody(image ,pressOnBack)
    }
}


@Composable
fun ImageDetailsBody(
    image: Image,
    pressOnBack: () -> Unit
){

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .background(MaterialTheme.colors.background)
        .fillMaxHeight()) {

        ConstraintLayout {
            val (imagePic,title,height,imageDetails,arrow) = createRefs()

            NetworkImage(url = image.imageurl,
            modifier = Modifier
                .constrainAs(imagePic) {
                    top.linkTo(parent.top)
                }
                .fillMaxWidth()
                .aspectRatio(.80f))

            Text(text = "Name "+image.title,
            style = MaterialTheme.typography.h5,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(imagePic.bottom)
                }
                .padding(start = 12.dp, top = 16.dp))

            Text(text = "Height "+image.height,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .constrainAs(height) {
                        top.linkTo(title.bottom)
                    }
                    .padding(start = 12.dp, top = 16.dp))

            Text(text = "Details "+image.desc,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .constrainAs(imageDetails) {
                        top.linkTo(height.bottom)
                    }
                    .padding(start = 12.dp, top = 16.dp))

            Icon(imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .constrainAs(arrow) {
                    top.linkTo(parent.top)
                }
                .padding(12.dp)
                .clickable(onClick = { pressOnBack() }))
        }

    }
}