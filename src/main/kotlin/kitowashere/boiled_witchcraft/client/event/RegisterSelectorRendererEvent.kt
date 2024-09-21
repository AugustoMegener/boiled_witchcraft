package kitowashere.boiled_witchcraft.client.event

import kitowashere.boiled_witchcraft.client.render.gui.overlay.GlyphEditorOverlay
import kitowashere.boiled_witchcraft.client.render.gui.overlay.SelectorRendererBuilder
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.Event

object RegisterSelectorRendererEvent : Event() {
    fun register(location: ResourceLocation, builder: SelectorRendererBuilder)
        { GlyphEditorOverlay.selectorRenderers[location] = builder }

    fun register(vararg entries: Pair<ResourceLocation, SelectorRendererBuilder>)
        { entries.forEach { register(it.first, it.second) } }
}