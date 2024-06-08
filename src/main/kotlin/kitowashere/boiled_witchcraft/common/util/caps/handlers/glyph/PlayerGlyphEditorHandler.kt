package kitowashere.boiled_witchcraft.common.util.caps.handlers.glyph

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.core.glyph.Util.translatableName
import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.glyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory.Companion.primaries
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory.Companion.structurals
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.data.editor.GlyphEditor
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player

class PlayerGlyphEditorHandler(val player: Player) : GlyphEditorHandler {

    val stages = arrayListOf<GlyphEditor.StageBuilder.() -> Unit>(

        // Category wrapper
        { name(stageName("category"))
          info   { _, _     -> glyphCategory.name                                                }
          action { _, i, _  -> glyphCategory = glyphCategories[clamped(i, glyphCategories.size)] } },

        // Glyph wrapper
        { name(stageName("glyph") )
          info   { g, _     -> g.stack.glyph.translatableName                                        }
          action { g, i, _  -> g.stack = GlyphStack(glyphCategory[clamped(i, glyphCategory.size)]) } },

        // Field wrapper
        { name (stageName("field") )
          info   { g, _    -> g.stack.data.dataFields[fieldIndex].nameComponent }
          action { _, i, _ -> fieldIndex = i                                    } },

        // value wrapper
        { name(stageName("value"))
          info   { g, _    -> g.stack.data.dataFields[fieldIndex].valueComponent }
          action { g, _, w -> g.stack.data.dataFields[fieldIndex].wrap(w)        } }
    )

    override val editor =
        object : GlyphEditor(stages, player.glyphStack) {
            override fun onChanged() { player.glyphStack = stack }
        }

    private val fieldAmount = editor.stack.data.dataFields.size

    override val glyphCategories = arrayOf(primaries, structurals)
    override var glyphCategory = glyphCategories[0]; set(value) { field = value; editor.stack = GlyphStack(value[0]) }
    override var fieldIndex = 0; set(value) { field = value.coerceIn(0..<fieldAmount) }


    companion object {
        val playerEditorCache = HashMap<Player, PlayerGlyphEditorHandler>()

        private fun clamped(value: Int, limit: Int) = if (value >= limit) 0 else if (value < 0) limit else value
        private fun stageName(name: String) = Component.translatable("editor.stages.$ID.$name")
    }
}