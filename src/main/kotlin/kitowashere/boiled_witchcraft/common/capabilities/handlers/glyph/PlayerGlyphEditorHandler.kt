package kitowashere.boiled_witchcraft.common.capabilities.handlers.glyph

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.core.glyph.EditorData.SectionRenderer
import kitowashere.boiled_witchcraft.client.core.glyph.Util.translatableName
import kitowashere.boiled_witchcraft.client.render.atlas.GlyphAtlas.getSprite
import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.glyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory.Companion.primaries
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory.Companion.structurals
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player

class PlayerGlyphEditorHandler(val player: Player) : GlyphEditorHandler {

    val stages = arrayListOf<GlyphEditor.StageBuilder.() -> Unit>(

        // Category wrapper
        { name(stageName("category"))
          renderer(glyphCategorySectionRenderer)
          info   { _, _     -> glyphCategory.name                                          }
          action { g, i, w  -> if (i > glyphCategories.size-1 || i < 0) g.edit(w.opposite)
                               else glyphCategory = glyphCategories[i]                     } },

        // Glyph wrapper
        { name(stageName("glyph"))
          renderer(glyphTypeSectionRenderer)
          info   { g, _     -> g.stack.glyph.translatableName                   }
          action { g, i, w  -> if (i > glyphCategory.size-1 || i < 0) g.edit(w.opposite)
                               else g.stack = GlyphStack(glyphCategory[i])      } },

        // Field wrapper
        { name (stageName("field") )
          info   { g, _    -> g.stack.data.dataFields[fieldIndex].nameComponent }
          action { _, i, _ -> fieldIndex = i                                    } },

        // value wrapper
        { name(stageName("value"))
          renderer(valueSectionRenderer)
          info   { g, _    -> g.stack.data.dataFields[fieldIndex].valueComponent }
          action { g, i, w ->  if (i > g.stack.data.dataFields.size-1 || i < 0) g.edit(w.opposite)
                               else g.stack.data.dataFields[fieldIndex].wrap(w.opposite) } }
    )

    override val editor =
        object : GlyphEditor(stages, player.glyphStack) {
            override fun onChanged() {
                player.glyphStack = stack
            }
        }

    private val fieldAmount = editor.stack.data.dataFields.size

    override val glyphCategories = arrayOf(primaries, structurals)
    override var glyphCategory = glyphCategories[0]; set(value) { field = value; editor.stack = GlyphStack(value[0]) }
    override var fieldIndex = 0; set(value) { field = value.coerceIn(0..<fieldAmount) }


    companion object {
        val playerEditorCache = HashMap<Player, PlayerGlyphEditorHandler>()

        val glyphCategorySectionRenderer: GlyphEditorHandler.() -> SectionRenderer = {
            SectionRenderer(
                { gui, _, font, x, y ->
                    var yPos = y

                    for (i in glyphCategories) {
                        var xPos = x

                        gui.drawString(font, i.name, x, yPos, if (i == glyphCategory) 0xeba434 else 0xffffff)
                        yPos += font.lineHeight + 2
                        for (ii in i.content) {
                            val size = 8

                            gui.blit(xPos, yPos, 0, size, size, ii.getSprite(ii.sizes[0]))

                            xPos += size + 2
                        }

                        yPos += 10
                    }

                },
                { f -> (f.lineHeight + 12) * glyphCategories.size }
            )
        }

        val glyphTypeSectionRenderer: GlyphEditorHandler.() -> SectionRenderer = {
            SectionRenderer(
                { gui, _, _, x, y ->
                    var xPos = x

                    for (i in glyphCategory.content) {
                        val stack = editor.stack
                        val size = 16

                        gui.blit(xPos, y + if (stack.glyph == i) 0 else 10, 0, size, size, i.getSprite(i.sizes[0]))

                        xPos += size + 2
                    }
                },
                { _ -> 26 }
            )
        }

        val valueSectionRenderer: GlyphEditorHandler.() -> SectionRenderer = {
            val renderer = editor.stack.data.dataFields[fieldIndex].renderer?.invoke(this)

            SectionRenderer(
                { gui, delta, font, x, y -> renderer?.renderer?.let { it(gui, delta, font, x, y) } },
                { f -> renderer?.height?.let { it(f) } ?: 0 }
            )
        }

        private fun clamped(value: Int, limit: Int) = if (value > limit) limit else if (value < 0) 0 else value
        private fun stageName(name: String) = Component.translatable("editor.stages.$ID.$name")
    }
}