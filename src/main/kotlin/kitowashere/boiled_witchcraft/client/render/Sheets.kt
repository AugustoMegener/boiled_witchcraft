package kitowashere.boiled_witchcraft.client.render

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.core.glyph.Util.getTexture
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.resources.model.Material
import net.minecraft.resources.ResourceLocation

object Sheets {
    val glyphSheet = ResourceLocation(ID,"textures/atlas/glyphs.png")
    val glyphSheetType: RenderType = RenderType.entityCutout(glyphSheet)

    fun Glyph.getMaterial(size: Int = 1) = Material(glyphSheet, getTexture(size))
    val GlyphStack.glyphMaterial get() = glyph.getMaterial(data.size)
}