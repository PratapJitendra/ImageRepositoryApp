package com.pivotalpeaks.imagerepositoryapp.extensions

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha


@Stable
fun  Modifier.visible(visiblity:Boolean):Modifier{
    return if (visiblity){
        this.then(alpha(1f))
    } else {
        this.then(alpha(0f))
    }
}