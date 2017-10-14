package io.battlerune.game.world.actor.pawn.update

enum class UpdateFlag(val mask: Int) {
    APPEARANCE(0x2),
    ANIMATION(0x8),
    FACE_ENTITY(0x80),
    FORCED_CHAT(0x1),
    HIT(0x0),
    FACE_COORDINATE(0x0),
    CHAT(0x10),
    GFX(0x800),
    FORCE_MOVEMENT(0x200),
    MOVEMENT_TYPE(0x0),
    TEMPORARY_MOVEMENT_TYPE(0x0),
    PLAYER_OPTIONS(0x0)
}