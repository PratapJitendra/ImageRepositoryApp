package com.pivotalpeaks.imagerepositoryapp.util

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.pivotalpeaks.imagerepositoryapp.ui.theme.shimmerHightLight
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage


@Composable
fun NetworkImage(
    url:String,
    contentScale: ContentScale= ContentScale.Crop,
    modifier: Modifier=Modifier
){
    CoilImage(imageModel =url,
        modifier = modifier,
        contentScale=contentScale,
         shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colors.background,
            highlightColor = shimmerHightLight,
            dropOff = .65f,
        ),
        failure = {
            Text(text = "image request failed",
            style = MaterialTheme.typography.body2)})
}