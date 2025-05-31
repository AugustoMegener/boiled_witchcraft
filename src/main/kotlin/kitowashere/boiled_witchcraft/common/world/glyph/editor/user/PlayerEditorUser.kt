package kitowashere.boiled_witchcraft.common.world.glyph.editor.user

import kitowashere.boiled_witchcraft.common.registry.DataAttachTypes.unlockedGlyphs
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level

@JvmInline
value class PlayerEditorUser(val player: Player) : EditorUser {
    override val avaliableGlyphs get() = player.unlockedGlyphs
    override val level: Level get() = player.level()

    companion object {

        val Player.editorUser get() = PlayerEditorUser(this)
    }
}