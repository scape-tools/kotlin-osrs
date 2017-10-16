package io.battlerune.game.world.actor.pawn.update

import io.battlerune.game.world.actor.pawn.player.Player

abstract class PlayerUpdateBlock(mask: Int, flag: UpdateFlag) : UpdateBlock<Player>(mask, flag)