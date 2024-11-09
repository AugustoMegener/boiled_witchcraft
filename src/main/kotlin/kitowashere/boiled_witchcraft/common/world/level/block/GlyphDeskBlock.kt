package kitowashere.boiled_witchcraft.common.world.level.block

import kitowashere.boiled_witchcraft.common.world.level.block.entity.GlyphDeskBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class GlyphDeskBlock(pProperties: Properties) : BaseEntityBlock(pProperties) {
    override fun codec() = TODO("Block codecs have not yet been implemented")

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = GlyphDeskBlockEntity(pos, state)

    override fun useWithoutItem(pState: BlockState, pLevel: Level, pPos: BlockPos, pPlayer: Player, r: BlockHitResult)
            : InteractionResult
    {
        if (pPlayer is ServerPlayer) pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos)!!) { it.writeBlockPos(pPos) }

        return InteractionResult.sidedSuccess(pLevel.isClientSide)
    }
}