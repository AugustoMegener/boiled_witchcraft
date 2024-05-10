package kitowashere.boiled_witchcraft.client.render.atlas

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.render.sheet.ModSheets.glyphSheet
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.Util.getGlyphLocation
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite
import net.minecraft.client.resources.TextureAtlasHolder
import net.minecraft.resources.ResourceLocation

object GlyphAtlas : TextureAtlasHolder(Minecraft.getInstance().textureManager, glyphSheet,
                                       ResourceLocation(ID, "glyphs")) {

    val atlas = textureAtlas

    fun Glyph.getSpriteLocation(size: Int = 1) =
        if (size !in sizes)
            MissingTextureAtlasSprite.getLocation()
        else
            getGlyphLocation(this)!!.withPrefix("glyph/").withSuffix("-${size}x${size}")



    val GlyphStack.spriteLocation get() = glyph.getSpriteLocation(data.size)
    val GlyphStack.sprite get() = this@GlyphAtlas.getSprite(spriteLocation)
}