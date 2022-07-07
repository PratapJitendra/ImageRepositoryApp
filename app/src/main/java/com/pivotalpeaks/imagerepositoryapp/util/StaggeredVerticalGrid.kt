package com.pivotalpeaks.imagerepositoryapp.util

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import kotlin.math.ceil


@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier=Modifier,
    maxColumnWidth: Dp,
    content:@Composable () -> Unit,

 ){
    Layout(content = content,
    modifier = modifier) { measurables,constraints ->
        check(constraints.hasBoundedWidth){
            "Unbounded width not supported"
        }
        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth=constraints.maxWidth / columns
        val itemContstraint=constraints.copy(maxWidth = columnWidth)
        val colHeights=IntArray(columns) {0}
        val placeable=measurables.map { measurable ->
            val column=shortestColumn(colHeights)
            val placeable=measurable.measure(itemContstraint)
            colHeights[column]+=placeable.height
            placeable
        }
        val height=colHeights.maxOrNull()?.coerceIn(constraints.minHeight,constraints.maxHeight)
            ?:constraints.minHeight
        layout(width = constraints.maxWidth,height=height){
            val colY=IntArray(columns){0}
            placeable.forEach { placeable ->
                val column= shortestColumn(colY)
                placeable.place(
                    x=columnWidth * column,
                    y=colY[column]
                )
                colY[column]+=placeable.height
            }
        }
    }
}

private fun shortestColumn(colHeights:IntArray):Int {
    var minHieght=Int.MAX_VALUE
    var column=0
    colHeights.forEachIndexed { index, height ->
        if (height < minHieght){
            minHieght = height
            column = index
        }
    }
    Log.d(TAG, "shortestColumn: column "+ column)
    return column

}