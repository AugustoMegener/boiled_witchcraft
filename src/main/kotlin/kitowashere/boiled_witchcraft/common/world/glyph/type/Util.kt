package kitowashere.boiled_witchcraft.common.world.glyph.type

import kitowashere.boiled_witchcraft.common.data.glyph.PillarGlyphData
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.glyphTypes
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType.BlockEventCtx
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType.GlyphEventTemplate
import net.minecraft.core.BlockPos.MutableBlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Block.UPDATE_ALL
import net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING
import net.minecraft.world.level.block.state.properties.IntegerProperty
import kotlin.math.max


object Util {

    object EventTemplates {
        data class SurfacedPillarConfig(val block: Block, val integerProperty: IntegerProperty?, val max: Int?)
        val surfacedPillar = GlyphEventTemplate<PillarGlyphData, BlockEventCtx, SurfacedPillarConfig>(
            { data, _, _ -> data.height!! * 5 },
            { _, data, ctx, config ->
                val range = data.height!!

                val level = ctx.level
                val direction = ctx.direction!!

                val block = config.block
                val prop = config.integerProperty

                val blockPos: MutableBlockPos = ctx.pos.mutable()
                val transitionRange = config.max

                if (transitionRange != null) {
                    for (i in 0 .. range - transitionRange) {
                        level.setBlock(blockPos, block.defaultBlockState().setValue(FACING, direction)
                            .also { if (prop != null) it.setValue(prop, 0) }, UPDATE_ALL)
                        blockPos.move(direction)
                    }

                    for (i in max(transitionRange - range, 0) until transitionRange) {
                        level.setBlock(
                            blockPos, block.defaultBlockState().setValue(FACING, direction)
                            .setValue(prop!!, i), UPDATE_ALL)
                        blockPos.move(direction)
                    }
                } else {
                    for (i in 0 until range) {
                        level.setBlock(blockPos, block.defaultBlockState().setValue(FACING, direction), UPDATE_ALL)
                        blockPos.move(direction)
                    }
                }
            }
        )
    }

    object ResourceHelper {
        fun getGlyphTexture(glyphType: GlyphType<*>, size: Int = 1): ResourceLocation {
            if (size !in glyphType.sizes) throw Exception("Unavailable size for this glyph :/...")
            return glyphTypes.getKey(glyphType)!!
                .withPrefix("textures/glyph/${size}x${size}/")
                .withSuffix(".png")
        }
    }
}