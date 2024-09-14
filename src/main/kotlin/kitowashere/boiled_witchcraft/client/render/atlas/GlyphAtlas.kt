package kitowashere.boiled_witchcraft.client.render.atlas

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.render.sheet.ModSheets.glyphSheet
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.Util.getGlyphLocation
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.noneGlyph
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.type.Glyph
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.TextureAtlasHolder
import net.minecraft.resources.ResourceLocation
import net.minecraft.resources.ResourceLocation.parse

object GlyphAtlas : TextureAtlasHolder(Minecraft.getInstance().textureManager, glyphSheet, parse(("$ID:glyphs"))) {

    val atlas = textureAtlas

    fun Glyph.getSpriteLocation(size: Int = 1) =
        if (size !in sizes || this == noneGlyph)
            MissingTextureAtlasSprite.getLocation()
        else
            getGlyphLocation(this).withPrefix("glyph/").withSuffix("-${size}x${size}")

    fun Glyph.getSprite(size: Int = 1) = getSprite(getSpriteLocation(size))

    val GlyphStack.spriteLocation: ResourceLocation get() =
        glyph.sizes.getOrNull(data.size)?.let { glyph.getSpriteLocation(it) } ?: MissingTextureAtlasSprite.getLocation()
    val GlyphStack.sprite: TextureAtlasSprite get() = getSprite(spriteLocation)
}