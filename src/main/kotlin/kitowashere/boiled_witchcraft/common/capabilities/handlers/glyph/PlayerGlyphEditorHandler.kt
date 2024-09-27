package kitowashere.boiled_witchcraft.common.capabilities.handlers.glyph

import kitowashere.boiled_witchcraft.common.core.editor.glyph.GlyphEditor
import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.glyphStackAttach
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory.Companion.primaries
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory.Companion.structurals
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.world.entity.player.Player

class PlayerGlyphEditorHandler(val player: Player) : GlyphEditor(listOf(primaries, structurals)) {
    override var result: GlyphStack
        get() = player.getData(glyphStackAttach)
        set(value) { player.setData(glyphStackAttach, value) }

    companion object {
        val playerEditorCache = HashMap<Player, PlayerGlyphEditorHandler>()
    }
}