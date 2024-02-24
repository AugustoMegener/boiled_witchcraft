package kitowashere.boiled_witchcraft

import kitowashere.boiled_witchcraft.client.config.ClientConfig
import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.attachRegistry
import kitowashere.boiled_witchcraft.common.registry.BlockEntityRegistry.blockEntityRegistry
import kitowashere.boiled_witchcraft.common.registry.BlockRegistry.blockRegistry
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.glyphTypeRegistry
import kitowashere.boiled_witchcraft.common.registry.ItemRegistry.itemRegistry
import net.neoforged.fml.ModLoadingContext
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS


@Mod(BoiledWitchcraft.ID)
object BoiledWitchcraft {
    const val ID = "boiled_witchcraft"

    val LOGGER: Logger = LogManager.getLogger(ID)

    init {
        listOf(
            blockRegistry, blockEntityRegistry, attachRegistry, glyphTypeRegistry, itemRegistry
        ).forEach { it.register(MOD_BUS) }

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.spec)
    }
}