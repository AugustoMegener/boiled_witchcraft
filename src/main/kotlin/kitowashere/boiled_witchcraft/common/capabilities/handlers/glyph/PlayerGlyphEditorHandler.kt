package kitowashere.boiled_witchcraft.common.capabilities.handlers.glyph

import kitowashere.boiled_witchcraft.common.core.editor.glyph.GlyphEditor
import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.glyphStack
import kitowashere.boiled_witchcraft.common.util.KotlinUtil
import net.minecraft.world.entity.player.Player

class PlayerGlyphEditorHandler(val player: Player) : GlyphEditorHandler {

    override val glyphSource = KotlinUtil.Delegation({ player.glyphStack }, { player.glyphStack = it })
    override val editor = GlyphEditor(glyphSource)

    companion object {
        val playerEditorCache = HashMap<Player, PlayerGlyphEditorHandler>()
    }
}