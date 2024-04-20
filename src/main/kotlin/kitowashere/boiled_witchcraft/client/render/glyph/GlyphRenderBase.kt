package kitowashere.boiled_witchcraft.client.render.glyph

import kitowashere.boiled_witchcraft.client.render.Sheets.glyphMaterial
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.client.resources.model.Material
import org.joml.Vector2i

abstract class GlyphRenderBase(glyphStack: GlyphStack = GlyphStack.empty) {

    private var glyphStack = glyphStack

    private val spriteList = ArrayList<GlyphSprite>()
    val sprites get() = spriteList.toTypedArray()

    private fun updateSprites() {
        spriteList.clear()

        putStackSprites(glyphStack)
        glyphStack.innerStack?.let { val margin = it.data.size / 2
                                     putStackSprites(it, Vector2i(margin)) }

        val offset = Vector2i(spriteList.minOf { it.pos.x }, spriteList.minOf { it.pos.y })

        for (i in sprites.map { it.pos }) {
            i.add(offset)
        }


    }

    private fun putStackSprites(stack: GlyphStack, pos: Vector2i = Vector2i()) {
        spriteList.add(GlyphSprite(stack.glyphMaterial, stack.data.size, pos))

        glyphStack.children.forEach {
            val child = it.value

            putStackSprites(child, pos.add(it.key.also { p -> p.x -= child.data.size / 2
                                                              p.y -= child.data.size / 2 } ))
        }
    }

    fun getGlyphStack() = glyphStack

    fun setGlyphStack(stack: GlyphStack) { glyphStack = stack
                                           updateSprites()    }

    data class GlyphSprite(val material: Material, val size: Int, val pos: Vector2i)

    companion object {

    }
}