package kitowashere.boiled_witchcraft.common.caps.handlers.glyph

import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.glyphStack
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.GlyphCategory.Companion.primaries
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.GlyphCategory.Companion.structurals
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.Util.translatableName
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.data.editor.GlyphEditor
import net.minecraft.world.entity.player.Player

class PlayerGlyphEditorHandler(val player: Player) : GlyphEditorHandler {

    override val glyphCategories = arrayOf(primaries, structurals)
    override var glyphCategory = glyphCategories[0]
    override var fieldIndex = 0; set(value) { field = value.coerceIn(0..<stages.size) }

    val stages = arrayListOf<GlyphEditor.StageBuilder.() -> Unit>(
        { action { _, i, _  -> glyphCategory = glyphCategories[i]     }
          info   { _, _     -> glyphCategory.name                     } },

        { action { g, i, _  -> g.stack = GlyphStack(glyphCategory[i]) }
          info   { g, _     -> g.stack.glyph.translatableName         } },

        { action { _, i, _ -> fieldIndex = i }
          info   { g, _    -> g.stack.data.dataFields[fieldIndex].nameComponent } },
        { action { g, _, w -> g.stack.data.dataFields[fieldIndex].wrap(w) }})

    override val editor = object : GlyphEditor(stages, player.glyphStack) {
        override fun onChanged() { player.glyphStack = stack }
    }
}