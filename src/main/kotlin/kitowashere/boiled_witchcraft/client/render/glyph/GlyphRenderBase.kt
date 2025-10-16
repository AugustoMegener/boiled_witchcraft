package kitowashere.boiled_witchcraft.client.render.glyph

import kitowashere.boiled_witchcraft.client.render.atlas.GlyphAtlas.sprite
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import org.joml.Vector2i

abstract class GlyphRenderBase {

    var glyphStack: GlyphStack = GlyphStack.empty
        set(value) { field = value
                     updateSprites() }


    data class GlyphSprite(val sprite: TextureAtlasSprite, val size: Int, val pos: Vector2i)

    private val spriteList = ArrayList<GlyphSprite>()
    val sprites get() = spriteList.toTypedArray()

    val width get() =
        if (spriteList.isEmpty()) 0 else spriteList.maxOf { it.pos.x + it.size } - spriteList.minOf { it.pos.x }

    val height get() =
        if (spriteList.isEmpty()) 0 else spriteList.maxOf { it.pos.y + it.size } - spriteList.minOf { it.pos.y }

    private fun updateSprites() {
        spriteList.clear()

        putStackSprites(glyphStack)
        glyphStack.inner?.let { putStackSprites(it, Vector2i(it.data.size / 2)) }

        val offset = Vector2i(spriteList.minOf { it.pos.x }, spriteList.minOf { it.pos.y })

        spriteList.map { it.pos } .forEach { it.add(offset) }
    }

    private fun putStackSprites(stack: GlyphStack, pos: Vector2i = Vector2i()) {
        spriteList.add(GlyphSprite(stack.sprite, stack.data.size, pos))

        stack.children.forEach {
            val child = it.value

            putStackSprites(child, pos.add(it.key.also { p -> p.x -= child.data.size / 2
                                                              p.y -= child.data.size / 2 } ))
        }
    }


}