package kitowashere.boiled_witchcraft.client.render.sheet

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation

object ModSheets {
    val glyphSheet = ResourceLocation(ID,"textures/atlas/glyphs.png")
    val glyphSheetType: RenderType = RenderType.entityCutout(glyphSheet)


}