package kitowashere.boiled_witchcraft.common.events

import kitowashere.boiled_witchcraft.BoiledWitchcraft
import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.glyphStack
import kitowashere.boiled_witchcraft.common.resource.CanvasRegistry
import kitowashere.boiled_witchcraft.common.resource.CanvasRegistry.CanvasMarkerType.CANVAS_GRIND
import kitowashere.boiled_witchcraft.common.resource.CanvasRegistry.CanvasMarkerType.MATERIAL_GRIND
import kitowashere.boiled_witchcraft.common.resource.CanvasRegistry.ItemCanvas.glyphCanvas
import kitowashere.boiled_witchcraft.common.resource.mosh.MohsRegistry
import kitowashere.boiled_witchcraft.common.resource.mosh.MohsRegistry.ItemMohs.mohs
import kitowashere.boiled_witchcraft.common.util.GameUtil.opposite
import kitowashere.boiled_witchcraft.common.util.GlyphUtil.canWriteGlyphOn
import kitowashere.boiled_witchcraft.common.util.GlyphUtil.glyphStack
import net.minecraft.world.item.ItemStack
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.AddReloadListenerEvent
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = BoiledWitchcraft.ID)
object NeoForgeEventCommon {

    @SubscribeEvent
    fun onAddReloadListener(event: AddReloadListenerEvent) {
        arrayOf(CanvasRegistry.ItemCanvas, CanvasRegistry.BlockCanvas, MohsRegistry.ItemMohs, MohsRegistry.BlockMohs)
            .forEach(event::addListener)
    }

    @SubscribeEvent
    fun onRightClickItem(event: PlayerInteractEvent.RightClickItem) {
        val player = event.entity

        player.canWriteGlyphOn?.run {
            val writerItem = player.getItemInHand(this)
            val canvasItem = player.getItemInHand(opposite)

            val canvas     = canvasItem.glyphCanvas!!
            val canvasMohs = canvasItem.mohs       !!
            val writerMohs = writerItem.mohs       !!

            val isCanvasGrind   = CANVAS_GRIND   in canvas.markers
            val isMaterialGrind = MATERIAL_GRIND in canvas.markers

            if ((isCanvasGrind && canvasMohs <= writerMohs) || (isMaterialGrind && canvasMohs >= writerMohs)) {
                if ((isCanvasGrind && canvasMohs == writerMohs) || isMaterialGrind)
                    writerItem.hurtAndBreak(1, event.level.random, player) {}

                player.addItem(ItemStack(canvasItem.run  { val i = item; shrink(1); i } )
                                                   .also { it.glyphStack = player.glyphStack     } )
            }
        }
    }
}