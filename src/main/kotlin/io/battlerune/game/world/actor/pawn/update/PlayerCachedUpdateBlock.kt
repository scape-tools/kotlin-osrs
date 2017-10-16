package io.battlerune.game.world.actor.pawn.update

import com.google.common.collect.ImmutableSet
import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.player.block.*

object PlayerCachedUpdateBlock {
    val CACHED_UPDATE_BLOCK : CachedUpdateBlock<Player> = CachedUpdateBlock(ImmutableSet.of<UpdateBlock<Player>>(
            PlayerFacePawnUpdateBlock(),
            PlayerGraphicUpdateBlock(),
            PlayerAppearanceUpdateBlock(),
            PlayerTemporaryMovementUpdateBlock(),
            PlayerCacheMovementTypeUpdateBlock(),
            PlayerContextMenuUpdateBlock(),
            PlayerForceMovementUpdateBlock(),
            PlayerHitUpdateBlock(),
            PlayerAnimationUpdateBlock(),
            PlayerChatUpdateBlock(),
            PlayerFaceCoordinateUpdateBlock(),
            PlayerForceChatUpdateBlock()
    ))
}