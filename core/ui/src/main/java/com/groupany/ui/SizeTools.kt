package com.groupany.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

object SizeTools {
    @Composable
    fun getStatusBarHeight(): Dp {
        val density = LocalDensity.current
        val insets = WindowInsets.statusBars
        val px = insets.getTop(density) // top inset in px
        return with(density) { px.toDp() }
    }

    @Composable
    fun getTopAppBarHeight(): Dp {
        return TopAppBarDefaults.TopAppBarExpandedHeight
    }

    @Composable
    fun getFullAppBarHeight(): Dp {
        return getTopAppBarHeight() + getStatusBarHeight()
    }

    @Composable
    fun getScreenWidth(): Dp {
        val windowInfo = LocalWindowInfo.current
        val density = LocalDensity.current
        return with(density) { windowInfo.containerSize.width.toDp() }
    }

    @Composable
    fun getScreenHeight(): Dp {
        val windowInfo = LocalWindowInfo.current
        val density = LocalDensity.current
        return with(density) { windowInfo.containerSize.height.toDp() }
    }

    @Composable
    fun convertDpToPx(dp: Dp): Float {
        return with(LocalDensity.current) { dp.toPx() }
    }

    @Composable
    fun convertPxToDp(pixels: Float): Dp {
        return with(LocalDensity.current) { pixels.toDp() }
    }
}