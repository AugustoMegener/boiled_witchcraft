package kitowashere.boiled_witchcraft.common.world.level.block

import com.google.common.collect.ImmutableMap
import com.mojang.serialization.MapCodec
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import kitowashere.boiled_witchcraft.common.world.level.block.entity.GlyphBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Direction.*
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape


abstract class GlyphBlock(properties: Properties, val glyphType: GlyphType) : BaseEntityBlock(properties) {

    override fun newBlockEntity(pPos: BlockPos, pState: BlockState) = GlyphBlockEntity(pPos, pState, glyphType)

    @Deprecated("Should not be deprecated D:", ReplaceWith("", ""))
    final override fun getShape(pState: BlockState, pLevel: BlockGetter, pPos: BlockPos, pContext: CollisionContext) =
        shapes[pState.getValue(facing)]!!

    override fun createBlockStateDefinition(pBuilder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(pBuilder)
        pBuilder.add(facing)
    }


    override fun stepOn(pLevel: Level, pPos: BlockPos, pState: BlockState, pEntity: Entity) {

    }


    companion object {
        val facing: DirectionProperty = BlockStateProperties.FACING
        
        fun getBlockEntity(level: Level, pos: BlockPos) = level.getBlockEntity(pos) as GlyphBlockEntity

        val shapes: ImmutableMap<Direction, VoxelShape> = ImmutableMap.of(
            DOWN,   box(0.0, 15.99, 0.0, 16.0, 16.0, 16.0),
            UP,     box(0.0, 0.0, 0.0, 16.0, 0.01, 16.0),
            NORTH,  box(0.0, 0.0, 15.99, 16.0, 16.0, 16.0),
            SOUTH,  box(0.0, 0.0, 0.0, 16.0, 16.0, 0.01),
            WEST,   box(15.99, 0.0, 0.0, 16.0, 16.0, 16.0),
            EAST,   box(0.0, 0.0, 0.0, 0.01, 16.0, 16.0),
        )
    }

    override fun codec(): MapCodec<out BaseEntityBlock> { TODO("Block codecs aren't implemented yet") }
}