package kitowashere.boiled_witchcraft.common.capabilities.handlers.glyph

import kitowashere.boiled_witchcraft.common.core.editor.glyph.GlyphEditor
import net.minecraft.world.entity.player.Player

class PlayerGlyphEditorHandler(val player: Player) : GlyphEditorHandler {


    override val editor = GlyphEditor()


    companion object {
        val playerEditorCache = HashMap<Player, PlayerGlyphEditorHandler>()


    }
}