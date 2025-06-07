package kitowashere.boiled_witchcraft.client.render.sheet

import io.kito.kore.common.event.KSubscribe
import io.kito.kore.common.reflect.Scan
import kitowashere.boiled_witchcraft.BoiledWitchcraft.local
import kitowashere.boiled_witchcraft.client.render.atlas.GlyphAtlas
import net.minecraft.client.renderer.RenderType
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent

@Scan
object ModSheets {
    val glyphSheetLocation = local("textures/atlas/glyphs.png")
    val glyphSheet: RenderType = RenderType.entityCutout(glyphSheetLocation)

    @KSubscribe
    fun RegisterClientReloadListenersEvent.registerAtlas() {
        registerReloadListener(GlyphAtlas)
    }
}