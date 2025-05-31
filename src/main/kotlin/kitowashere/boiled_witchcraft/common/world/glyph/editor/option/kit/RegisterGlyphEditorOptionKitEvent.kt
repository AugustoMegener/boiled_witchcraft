package kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit

import com.google.common.collect.ImmutableMap
import io.kito.kore.common.event.KSubscribe
import io.kito.kore.common.reflect.Scan
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import net.neoforged.bus.api.Event
import net.neoforged.fml.event.IModBusEvent
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS


class RegisterGlyphEditorOptionKitEvent : Event(), IModBusEvent {

    infix fun EditorOptionKitType<*>.on(glyph: () -> Glyph<*>) { entries += glyph to this }

    @Scan
    companion object  {
        private val entries = arrayListOf<Pair<() -> Glyph<*>, EditorOptionKitType<*>>>()

        val glyphEditorOptionKits: Map<Glyph<*>, EditorOptionKitType<*>>
                by lazy { ImmutableMap.copyOf(entries.associate { it.first() to it.second }) }

        @KSubscribe
        fun FMLCommonSetupEvent.call() {
            MOD_BUS.post(RegisterGlyphEditorOptionKitEvent())
        }
    }
}