package kitowashere.boiled_witchcraft.common.world.level.block

import com.google.common.collect.ImmutableMap
import com.mojang.serialization.MapCodec
import kitowashere.boiled_witchcraft.common.data.Caps
import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.titanBlood
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import kitowashere.boiled_witchcraft.common.world.level.block.entity.GlyphBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Direction.*
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class GlyphBlock(properties: Properties) : BaseEntityBlock(properties) {

    override fun newBlockEntity(pPos: BlockPos, pState: BlockState) = GlyphBlockEntity(pPos, pState)

    @Deprecated("Should not be deprecated D:", ReplaceWith("", ""))
    override fun getShape(pState: BlockState, pLevel: BlockGetter, pPos: BlockPos, pContext: CollisionContext) =
        shapes[pState.getValue(FACING)]!!

    override fun createBlockStateDefinition(pBuilder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(pBuilder)
        pBuilder.add(FACING)
    }


    override fun stepOn(pLevel: Level, pPos: BlockPos, pState: BlockState, pEntity: Entity) {
        val blockEntity = getBlockEntity(pLevel, pPos)
        val cap = pEntity.getCapability(Caps.TitanBlood.entity)!!

        blockEntity.executes { glyph, data ->
            glyph.blockStepped(cap, data, pEntity)
            glyph.blockInteracted(cap, data, GlyphType.BlockEventCtx(
                pLevel, pPos, pState, blockEntity, pEntity, pState.getValue(FACING)
            ))
        }
    }

    @Deprecated("Should not be deprecated >:v", ReplaceWith("", ""))
    override fun use(pState: BlockState, pLevel: Level, pPos: BlockPos,
                     pPlayer: Player, pHand: InteractionHand, pHit: BlockHitResult)
    : InteractionResult
    {
        val blockEntity = getBlockEntity(pLevel, pPos)
        val cap = pPlayer.getCapability(Caps.TitanBlood.entity)!!

        blockEntity.executes { glyph, data ->
            glyph.blockClicked(cap, data, pPlayer)
            glyph.blockInteracted(cap, data, GlyphType.BlockEventCtx(
                pLevel, pPos, pState, blockEntity, pPlayer, pState.getValue(FACING)
            ))
        }

        return InteractionResult.SUCCESS
    }

    override fun canConnectRedstone(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction?) = false

    override fun onNeighborChange(state: BlockState, level: LevelReader, pos: BlockPos, neighbor: BlockPos) {
        val blockEntity = getBlockEntity(level as Level, pos)
        val cap = level.getChunkAt(pos).titanBlood

        val ctx = GlyphType.BlockEventCtx(
            level, pos, state, blockEntity, null, state.getValue(FACING),
            level.getBlockState(pos).getAnalogOutputSignal(level, pos)
        )

        blockEntity.executes { glyph, data ->
            glyph.blockPowered(cap, data, ctx)
            glyph.blockInteracted(cap, data, ctx)
        }
    }

    /*@Deprecated("Should not be deprecated :|", ReplaceWith("", ""))
    override fun getDestroyProgress(pState: BlockState, pPlayer: Player, pLevel: BlockGetter, pPos: BlockPos): Float {
        val data = getData(pLevel as Level, pPos)

        data.owner

        return super.getDestroyProgress(pState, pPlayer, pLevel, pPos)
    }*/

    companion object {
        private fun getBlockEntity(level: Level, pos: BlockPos) = level.getBlockEntity(pos) as GlyphBlockEntity
        private fun getData(level: Level, pos: BlockPos) = getBlockEntity(level, pos).data

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