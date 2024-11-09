package kitowashere.boiled_witchcraft.common.world.inventory.menu

import kitowashere.boiled_witchcraft.common.registry.BlockRegistry.glyphDeskBlock
import kitowashere.boiled_witchcraft.common.registry.MenuTypeRegistry.glyphDeskMenuType
import kitowashere.boiled_witchcraft.common.util.GlyphUtil.glyphStack
import kitowashere.boiled_witchcraft.common.world.level.block.entity.GlyphDeskBlockEntity
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.neoforged.neoforge.items.SlotItemHandler

class GlyphDeskMenu(id: Int, val playerInventory: Inventory, blockEntity: GlyphDeskBlockEntity)
    : AbstractContainerMenu(glyphDeskMenuType, id)
{
    constructor(id: Int, inv: Inventory, buf: RegistryFriendlyByteBuf) :
            this(id, inv, inv.player.level().getBlockEntity(buf.readBlockPos()) as GlyphDeskBlockEntity)

    private val level: Level = playerInventory.player.level()
    private val levelAccess: ContainerLevelAccess = ContainerLevelAccess.create(level, blockEntity.blockPos)
    private val inventory = blockEntity.inventory

    val editingGlyphStack       = blockEntity.editingGlyphStack
    val createdGlyphStack get() = inventory.getStackInSlot(2).glyphStack

    init {
        repeat(3) { y ->
        repeat(9) { x -> addSlot(Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 111 + y * 18)) } }

        repeat(9) { addSlot(Slot(playerInventory, it, 8 + it * 18, 169)) }

        arrayOf(SlotItemHandler(inventory, 0, 119, 25),
                SlotItemHandler(inventory, 1, 137, 25),
                SlotItemHandler(inventory, 2, 155, 25) ).forEach(::addSlot)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        val itemStack = slots[index].item.takeIf { !it.isEmpty }?.copy() ?: return ItemStack.EMPTY

        return if (!moveItemStackTo(itemStack, if (index < 2) 0 else 2, if (index < 2) 3 else 1, false))
            ItemStack.EMPTY else itemStack
    }

    override fun stillValid(player: Player) = stillValid(levelAccess, player, glyphDeskBlock)
}