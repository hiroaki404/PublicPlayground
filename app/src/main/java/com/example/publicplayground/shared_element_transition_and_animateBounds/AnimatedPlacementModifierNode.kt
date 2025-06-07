package com.example.publicplayground.shared_element_transition_and_animateBounds

import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.DeferredTargetAnimation
import androidx.compose.animation.core.ExperimentalAnimatableApi
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ApproachLayoutModifierNode
import androidx.compose.ui.layout.ApproachMeasureScope
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.round

// refer to
// https://developer.android.com/reference/kotlin/androidx/compose/ui/layout/LookaheadScope
// https://proandroiddev.com/animations-with-lookahead-in-jetpack-compose-60423fe0d1a7

context(LookaheadScope)
fun Modifier.animatePlacement(): Modifier =
    this.then(AnimatePlacementNodeElement(this@LookaheadScope))

// Creates a custom node element for the AnimatedPlacementModifierNode above.
data class AnimatePlacementNodeElement(val lookaheadScope: LookaheadScope) :
    ModifierNodeElement<AnimatedPlacementModifierNode>() {

    override fun update(node: AnimatedPlacementModifierNode) {
        node.lookaheadScope = lookaheadScope
    }

    override fun create(): AnimatedPlacementModifierNode {
        return AnimatedPlacementModifierNode(lookaheadScope)
    }
}

@OptIn(ExperimentalAnimatableApi::class)
class AnimatedPlacementModifierNode(var lookaheadScope: LookaheadScope) :
    ApproachLayoutModifierNode, Modifier.Node() {
    // Creates an offset animation, the target of which will be known during placement.
    val offsetAnimation: DeferredTargetAnimation<IntOffset, AnimationVector2D> =
        DeferredTargetAnimation(IntOffset.VectorConverter)

    val sizeAnimation: DeferredTargetAnimation<IntSize, AnimationVector2D> =
        DeferredTargetAnimation(IntSize.VectorConverter)

    override fun isMeasurementApproachInProgress(lookaheadSize: IntSize): Boolean {
        // Since we only animate the placement here, we can consider measurement approach
        // complete.
        sizeAnimation.updateTarget(lookaheadSize, coroutineScope, tween(3_000))
        return !sizeAnimation.isIdle
    }

    // Returns true when the offset animation is in progress, false otherwise.
    override fun Placeable.PlacementScope.isPlacementApproachInProgress(
        lookaheadCoordinates: LayoutCoordinates
    ): Boolean {
        val target =
            with(lookaheadScope) {
                lookaheadScopeCoordinates.localLookaheadPositionOf(lookaheadCoordinates).round()
            }
        offsetAnimation.updateTarget(target, coroutineScope, tween(3_000))
        return !offsetAnimation.isIdle
    }

    override fun ApproachMeasureScope.approachMeasure(
        measurable: Measurable,
        constraints: Constraints,
    ): MeasureResult {
        val (animateWidth, animateHeight) =
            sizeAnimation.updateTarget(
                lookaheadSize,
                coroutineScope,
                tween(3_000)
            )
        val placeable = measurable.measure(Constraints.fixed(animateWidth, animateHeight))

        return layout(placeable.width, placeable.height) {
            val coordinates = coordinates
            if (coordinates != null) {
                // Calculates the target offset within the lookaheadScope
                val target =
                    with(lookaheadScope) {
                        lookaheadScopeCoordinates.localLookaheadPositionOf(coordinates).round()
                    }

                // Uses the target offset to start an offset animation
                val animatedOffset =
                    offsetAnimation.updateTarget(target, coroutineScope, tween(3_000))
                // Calculates the *current* offset within the given LookaheadScope
                val placementOffset =
                    with(lookaheadScope) {
                        lookaheadScopeCoordinates
                            .localPositionOf(coordinates, Offset.Zero)
                            .round()
                    }
                // Calculates the delta between animated position in scope and current
                // position in scope, and places the child at the delta offset. This puts
                // the child layout at the animated position.
                val (x, y) = animatedOffset - placementOffset
                placeable.place(x, y)
            } else {
                placeable.place(0, 0)
            }
        }
    }
}
