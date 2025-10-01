package com.groupany.ui.animation


import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.spring
import androidx.compose.runtime.compositionLocalOf

object AnimationUtils {

    val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }

    fun AnimatedVisibilityScope.isAnimationFinished(): Boolean {
        return transition.currentState == transition.targetState
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

    fun <T> spatialNotExpressiveSpring() = spring<T>(
        dampingRatio = 1f,
        stiffness = 150f,
    )

    fun <T> nonSpatialExpressiveSpring() = spring<T>(
        dampingRatio = 1f,
        stiffness = 1600f,
    )

    @OptIn(ExperimentalSharedTransitionApi::class)
    val boundsTransform = BoundsTransform { _, _ ->
        spatialNotExpressiveSpring()
    }

}