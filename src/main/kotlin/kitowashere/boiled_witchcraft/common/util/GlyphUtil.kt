package kitowashere.boiled_witchcraft.common.util

import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.glyphStackData
import kitowashere.boiled_witchcraft.common.resource.CanvasRegistry.ItemCanvas.glyphCanvas
import kitowashere.boiled_witchcraft.common.resource.mosh.MohsRegistry.ItemMohs.mohs
import kitowashere.boiled_witchcraft.common.util.GameUtil.opposite
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import org.openjdk.nashorn.internal.runtime.regexp.joni.exception.ValueException

object GlyphUtil {
    var ItemStack.glyphStack: GlyphStack?
        get() =
            if (glyphCanvas != null)
                getOrDefault(glyphStackData, GlyphStack.empty)
            else
                null

        set(value) {
            if (value != null && value.data.size > glyphCanvas!!.size)
                throw ValueException("Canvas is too small for GlyphStack")

            if (glyphStack != null)
                set(glyphStackData, value ?: GlyphStack.empty)
        }

    val Player.canWriteGlyphOn get() = InteractionHand.entries.associateWith {
        getItemInHand(it.opposite).run { glyphCanvas?.markers?.isNotEmpty() == true && mohs != null } &&
        getItemInHand(it)         .run { mohs != null && glyphCanvas == null                        } }
                                  .run { entries.find   { it.value                                  }
                                         ?.key  .takeIf { it != entries.find { i -> !i.value }?.key } }


}