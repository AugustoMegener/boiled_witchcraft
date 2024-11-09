package kitowashere.boiled_witchcraft.common.world.level.block.entity

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.registry.BlockEntityTypeRegistry.glyphDeskBlockEntityType
import kitowashere.boiled_witchcraft.common.registry.DataComponentRegistry.glyphStackData
import kitowashere.boiled_witchcraft.common.registry.ItemRegistry.glyphCanvasTag
import kitowashere.boiled_witchcraft.common.registry.ItemRegistry.glyphEditorTag
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import kitowashere.boiled_witchcraft.common.world.inventory.menu.GlyphDeskMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.items.ItemStackHandler

class GlyphDeskBlockEntity(pos: BlockPos, state: BlockState) :
    BlockEntity(glyphDeskBlockEntityType, pos, state), MenuProvider
{
    val inventory = object : ItemStackHandler(3) {
        override fun isItemValid(slot: Int, stack: ItemStack) =
            when(slot) {    0 -> stack.`is`(glyphEditorTag)
                            1 -> stack.`is`(glyphCanvasTag)
                         else -> false }

        override fun onContentsChanged(slot: Int) {
            if (getStackInSlot(0).isEmpty || getStackInSlot(1).isEmpty){
                if (getStackInSlot(2).isEmpty) return
                setStackInSlot(2, ItemStack.EMPTY); setChanged()
            }
            else if ( getStackInSlot(2).isEmpty && !editingGlyphStack.isEmpty) {
                setStackInSlot(2, extractItem(1, 1, false).also { it.set(glyphStackData, editingGlyphStack) })
                setChanged()
            }
        }
    }

    val editingGlyphStack = GlyphStack.empty

    override fun createMenu(id: Int, inventory: Inventory, player: Player) = GlyphDeskMenu(id, inventory, this)

    override fun getDisplayName() = containerName

    override fun saveAdditional(pTag: CompoundTag, pRegistries: HolderLookup.Provider) {
        pTag.put(INVENTORY, inventory.serializeNBT(pRegistries))
        pTag.put(GLYPH, editingGlyphStack.serializeNBT(pRegistries))

        super.saveAdditional(pTag, pRegistries)
    }

    override fun loadAdditional(pTag: CompoundTag, pRegistries: HolderLookup.Provider) {
        inventory.deserializeNBT(pRegistries, pTag.getCompound(INVENTORY))
        editingGlyphStack.deserializeNBT(pRegistries, pTag.getCompound(GLYPH))

        super.loadAdditional(pTag, pRegistries)
    }

    companion object {
        const val INVENTORY = "inventory"
        const val     GLYPH = "glyph"

        val containerName: Component = Component.translatable("container.$ID.glyph_desk")
    }
}