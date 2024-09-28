package kitowashere.boiled_witchcraft

import kitowashere.boiled_witchcraft.common.registry.AttachRegistry
import kitowashere.boiled_witchcraft.common.registry.DataComponentRegistry
import kitowashere.boiled_witchcraft.common.registry.GlyphReg.glyphRegistry
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry
import kitowashere.boiled_witchcraft.common.registry.ItemRegistry
import net.neoforged.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(BoiledWitchcraft.ID)
object BoiledWitchcraft {
    const val ID = "boiled_witchcraft"

    val logger: Logger = LogManager.getLogger(ID)

    init {
        glyphRegistry

        listOf(ItemRegistry, GlyphRegistry, DataComponentRegistry, AttachRegistry)
            .map { it.register }.forEach { it.register(MOD_BUS) }
    }
}