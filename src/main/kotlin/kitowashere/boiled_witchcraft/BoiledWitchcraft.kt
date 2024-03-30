package kitowashere.boiled_witchcraft

import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.attachRegistry
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.glyphRegistry
import net.neoforged.fml.common.Mod
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(BoiledWitchcraft.ID)
object BoiledWitchcraft {
    const val ID = "boiled_witchcraft"

    //val logger: Logger = LogManager.getLogger(ID)

    init {
        listOf(glyphRegistry, attachRegistry).forEach { it.register(MOD_BUS) }
    }
}