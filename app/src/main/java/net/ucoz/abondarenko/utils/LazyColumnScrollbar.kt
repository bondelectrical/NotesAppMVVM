package net.ucoz.abondarenko.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun LazyColumnScrollbar(
    listState: LazyListState,
    thickness: Dp = 5.dp,
    padding: Dp = 4.dp,
    thumbMinHeight: Float = 0.1f,
    thumbColor: Color = Color(0xFF2A59B6),
    thumbShape: Shape = CircleShape,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    if (!enabled) content()
    else Box {
        content()
        LazyColumnScrollbar(
            listState = listState,
            thickness = thickness,
            padding = padding,
            thumbMinHeight = thumbMinHeight,
            thumbColor = thumbColor,
            thumbShape = thumbShape,
        )
    }
}


@Composable
fun LazyColumnScrollbar(
    listState: LazyListState,
    thickness: Dp = 6.dp,
    padding: Dp = 8.dp,
    thumbMinHeight: Float = 1f,
    thumbColor: Color = Color(0xFF2A59B6),
    thumbShape: Shape = CircleShape
) {

    val realFirstVisibleItem by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.firstOrNull {
                it.index == listState.firstVisibleItemIndex
            }
        }
    }

    val isStickyHeaderInAction by remember {
        derivedStateOf {
            val realIndex = realFirstVisibleItem?.index ?: return@derivedStateOf false
            val firstVisibleIndex = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.index
                ?: return@derivedStateOf false
            realIndex != firstVisibleIndex
        }
    }

    fun LazyListItemInfo.fractionHiddenTop() =
        if (size == 0) 0f else -offset.toFloat() / size.toFloat()

    fun LazyListItemInfo.fractionVisibleBottom(viewportEndOffset: Int) =
        if (size == 0) 0f else (viewportEndOffset - offset).toFloat() / size.toFloat()

    val normalizedThumbSizeReal by remember {
        derivedStateOf {
            listState.layoutInfo.let {
                if (it.totalItemsCount == 0)
                    return@let 0f

                val firstItem = realFirstVisibleItem ?: return@let 0f
                val firstPartial = firstItem.fractionHiddenTop()
                val lastPartial =
                    1f - it.visibleItemsInfo.last().fractionVisibleBottom(it.viewportEndOffset)

                val realSize = it.visibleItemsInfo.size - if (isStickyHeaderInAction) 1 else 0
                val realVisibleSize = realSize.toFloat() - firstPartial - lastPartial
                realVisibleSize / it.totalItemsCount.toFloat()
            }
        }
    }

    val normalizedThumbSize by remember {
        derivedStateOf {
            normalizedThumbSizeReal.coerceAtLeast(thumbMinHeight)
        }
    }

    fun offsetCorrection(top: Float): Float {
        if (normalizedThumbSizeReal >= thumbMinHeight)
            return top
        val topRealMax = 1f - normalizedThumbSizeReal
        val topMax = 1f - thumbMinHeight
        return top * topMax / topRealMax
    }

    val normalizedOffsetPosition by remember {
        derivedStateOf {
            listState.layoutInfo.let {
                if (it.totalItemsCount == 0 || it.visibleItemsInfo.isEmpty())
                    return@let 0f

                val firstItem = realFirstVisibleItem ?: return@let 0f
                val top = firstItem
                    .run { index.toFloat() + fractionHiddenTop() } / it.totalItemsCount.toFloat()
                offsetCorrection(top)
            }
        }
    }

    BoxWithConstraints(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .fillMaxHeight()
    ) {
        Box(
            Modifier
                .align(Alignment.TopEnd)
                .graphicsLayer {
                    translationY = constraints.maxHeight.toFloat() * normalizedOffsetPosition
                }
                .padding(horizontal = padding)
                .width(thickness)
                .clip(thumbShape)
                .background(thumbColor)
                .fillMaxHeight(
                    if (listState.layoutInfo.totalItemsCount == 0) {
                        0f
                    } else {
                        normalizedThumbSize
                    }
                )
        )
    }


}