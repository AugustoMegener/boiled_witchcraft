package kitowashere.boiled_witchcraft.common.data

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.data.handler.blood.ITitanBloodHandler
import kitowashere.boiled_witchcraft.common.data.handler.glyph.composing.IGlyphComposingHandler
import kitowashere.boiled_witchcraft.common.data.handler.glyph.configurator.IGlyphConfiguratorHandler
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.capabilities.EntityCapability
import net.neoforged.neoforge.capabilities.ItemCapability

object Caps {
    object TitanBlood {
        val entity: EntityCapability<ITitanBloodHandler, Void> = EntityCapability.createVoid(
            ResourceLocation(ID, "titan_blood"),
            ITitanBloodHandler::class.java
        )
    }

    object Glyph {
        object Configurator {
            val entity: EntityCapability<IGlyphConfiguratorHandler, Void> = EntityCapability.createVoid(
                ResourceLocation(ID, "editing_glyph"),
                IGlyphConfiguratorHandler::class.java
            )
        }

        object Composing {
            val item: ItemCapability<IGlyphComposingHandler, Void> = ItemCapability.createVoid(
                ResourceLocation(ID, "glyph_composing"),
                IGlyphComposingHandler::class.java
            )
        }
    }
}