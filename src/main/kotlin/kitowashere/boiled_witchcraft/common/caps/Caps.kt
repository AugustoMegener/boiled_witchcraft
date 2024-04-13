package kitowashere.boiled_witchcraft.common.caps

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.caps.handlers.glyph.GlyphEditorHandler
import kitowashere.boiled_witchcraft.common.caps.handlers.glyph.GlyphStackHandler
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Player
import net.neoforged.neoforge.capabilities.EntityCapability
import net.neoforged.neoforge.capabilities.ItemCapability

object Caps {
    object Entity {
        val entityGlyphEditor: EntityCapability<GlyphEditorHandler, Void> =
            EntityCapability.create(ResourceLocation(ID, "glyph_editor_entity"),
                                    GlyphEditorHandler::class.java, Void::class.java)

        val Player.glyphEditor get() = this.getCapability(entityGlyphEditor)!!
    }

    object Item {
        @JvmStatic
        val itemGlyphStack: ItemCapability<GlyphStackHandler, Void> =
            ItemCapability.create(ResourceLocation(ID, "glyph_stack_item"),
                                  GlyphStackHandler::class.java, Void::class.java)
    }
}