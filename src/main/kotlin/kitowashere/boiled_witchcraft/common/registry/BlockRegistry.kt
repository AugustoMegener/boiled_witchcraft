package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.common.world.level.block.GlyphDeskBlock
import net.minecraft.core.registries.Registries.BLOCK
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object BlockRegistry : Register<Block>(BLOCK) {

    val glyphDeskBlock by "glyph_desk" by { GlyphDeskBlock(Properties.of()) }
}