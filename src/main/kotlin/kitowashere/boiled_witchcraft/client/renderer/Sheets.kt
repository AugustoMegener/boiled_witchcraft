package kitowashere.boiled_witchcraft.client.renderer

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import kitowashere.boiled_witchcraft.common.world.glyph.type.Util.ResourceHelper.getGlyphTexture
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.resources.model.Material
import net.minecraft.resources.ResourceLocation

object Sheets {
    val glyphSheet = ResourceLocation(ID,"textures/atlas/glyphs.png")
    val glyphSheetType: RenderType = RenderType.entityCutout(glyphSheet)

    fun glyphMaterial(glyph: GlyphType<*>, size: Int) = Material(glyphSheet, getGlyphTexture(glyph, size))
}