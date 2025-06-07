package kitowashere.boiled_witchcraft.client.render.atlas

import io.kito.kore.util.minecraft.minecraftClient
import kitowashere.boiled_witchcraft.BoiledWitchcraft.local
import kitowashere.boiled_witchcraft.client.render.sheet.ModSheets.glyphSheetLocation
import kitowashere.boiled_witchcraft.common.registry.Registries.glyphRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.EmptyGlyph
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.TextureAtlasHolder
import net.minecraft.resources.ResourceLocation

object GlyphAtlas : TextureAtlasHolder(minecraftClient.textureManager, glyphSheetLocation, local("glyphs")) {

    val glyphAtlas = textureAtlas

    fun Glyph<*>.getSpriteLocation(size: Int = 1) =
        if (size !in sizes || copyTextureFrom == EmptyGlyph)
            MissingTextureAtlasSprite.getLocation()
        else
            glyphRegistry.getKey(copyTextureFrom)!!.withPrefix("glyph/").withSuffix("-${size}x${size}")

    fun Glyph<*>.getSprite(size: Int = 1) = getSprite(getSpriteLocation(size))

    val GlyphStack.spriteLocation: ResourceLocation get() = glyph.getSpriteLocation(size)

    val GlyphStack.sprite: TextureAtlasSprite get() =
        getSprite(spriteLocation)
}