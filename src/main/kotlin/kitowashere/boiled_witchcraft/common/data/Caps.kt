package kitowashere.boiled_witchcraft.common.data

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.data.handler.blood.ITitanBloodHandler
import kitowashere.boiled_witchcraft.common.data.handler.glyph.EditingGlyph
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.capabilities.EntityCapability

object Caps {
    object TitanBlood {
        val entity: EntityCapability<ITitanBloodHandler, Void> = EntityCapability.createVoid(
            ResourceLocation(ID, "titan_blood"),
            ITitanBloodHandler::class.java
        )
    }

    object GlyphInstance {
        val player: EntityCapability<EditingGlyph, Void> = EntityCapability.createVoid(
            ResourceLocation(ID, "glyph_instance"),
            EditingGlyph::class.java
        )
    }
}