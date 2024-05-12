package kitowashere.boiled_witchcraft.client.render.glyph

import kitowashere.boiled_witchcraft.client.render.atlas.GlyphAtlas.sprite
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import org.joml.Vector2i

abstract class GlyphRenderBase {

    var glyphStack: GlyphStack = GlyphStack.empty
        set(value) { field = value
                     updateSprites() }


    data class GlyphSprite(val sprite: TextureAtlasSprite, val size: Int, val pos: Vector2i)

    private val spriteList = ArrayList<GlyphSprite>()
    val sprites get() = spriteList.toTypedArray()


    private fun updateSprites() {
        spriteList.clear()

        putStackSprites(glyphStack)
        glyphStack.innerStack?.let { val margin = it.data.size / 2
                                     putStackSprites(it, Vector2i(margin)) }

        val offset = Vector2i(spriteList.minOf { it.pos.x }, spriteList.minOf { it.pos.y })

        spriteList.map { it.pos } .forEach { it.add(offset) }
    }

    private fun putStackSprites(stack: GlyphStack, pos: Vector2i = Vector2i()) {
        spriteList.add(GlyphSprite(stack.sprite, stack.data.size, pos))

        glyphStack.children.forEach {
            val child = it.value

            putStackSprites(child, pos.add(it.key.also { p -> p.x -= child.data.size / 2
                                                              p.y -= child.data.size / 2 } ))
        }
    }
}