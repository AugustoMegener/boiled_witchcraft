package kitowashere.boiled_witchcraft.common.util.caps

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.util.caps.handlers.glyph.GlyphEditorHandler
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Player
import net.neoforged.neoforge.capabilities.EntityCapability

object Caps {
    object Entity {
        val entityGlyphEditor: EntityCapability<GlyphEditorHandler, Void> =
            EntityCapability.create(ResourceLocation(ID, "glyph_editor_entity"),
                                    GlyphEditorHandler::class.java, Void::class.java)

        val Player.glyphEditor get() = this.getCapability(entityGlyphEditor)!!
    }
}