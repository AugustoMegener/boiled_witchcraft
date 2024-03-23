package kitowashere.boiled_witchcraft.common.data

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.data.handler.blood.ITitanBloodHandler
import kitowashere.boiled_witchcraft.common.data.handler.glyph.IGlyphHandler
import kitowashere.boiled_witchcraft.common.data.handler.glyph.composing.IGlyphComposingHandler
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.capabilities.EntityCapability
import net.neoforged.neoforge.capabilities.ItemCapability

object Caps {
    object TitanBlood {
        val entity: EntityCapability<ITitanBloodHandler, Void> = EntityCapability.createVoid(
            ResourceLocation(ID, "titan_blood"), ITitanBloodHandler::class.java)
    }

    object Glyph {
        val entity: EntityCapability<IGlyphHandler, Void> = EntityCapability.createVoid(
            ResourceLocation(ID, "glyph"), IGlyphHandler::class.java)


        object Composing {
            val item: ItemCapability<IGlyphComposingHandler, Void> = ItemCapability.createVoid(
                ResourceLocation(ID, "glyph_composing"), IGlyphComposingHandler::class.java)
        }
    }
}