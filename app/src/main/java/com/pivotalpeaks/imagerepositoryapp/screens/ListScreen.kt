package com.pivotalpeaks.imagerepositoryapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.statusBarsPadding
import com.pivotalpeaks.imagerepositoryapp.Image
import com.pivotalpeaks.imagerepositoryapp.util.NetworkImage

@Composable
fun ListImages(
    images:List<Image>,
    selectImage: (Long) -> Unit,
    modifier: Modifier=Modifier
){
    val state= rememberLazyListState()

    Column(modifier = modifier
        .statusBarsPadding()
        .background(MaterialTheme.colors.background)) {
        LazyColumn(state=state, contentPadding = PaddingValues(4.dp)){
            items(items=images,
            itemContent = { image ->
                ListImage(image = image, selectImage =selectImage )
            })
        }

    }
}


@Composable
fun ListImage(
    image: Image,
    selectImage: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(onClick = { selectImage(image.id) }),
        color = MaterialTheme.colors.background,
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        ConstraintLayout(modifier = Modifier.padding(8.dp)) {
            val (imagePic,title,height) = createRefs()
            NetworkImage(url = image.imageurl, modifier = Modifier
                .constrainAs(imagePic) {
                    centerVerticallyTo(parent)
                    end.linkTo(title.start)
                }
                .height(64.dp)
                .aspectRatio(1f)
                .fillMaxSize()
                .clip(RoundedCornerShape(4.dp))
            )

            Text(image.title, Modifier
                .constrainAs(title) {
                    start.linkTo(imagePic.end)
                }
                .padding(horizontal = 12.dp))

            Text(image.height, Modifier
                .constrainAs(height) {
                    start.linkTo(imagePic.end)
                    top.linkTo(title.bottom)
                }
                .padding(start = 12.dp, top = 4.dp))
        }

    }
}


