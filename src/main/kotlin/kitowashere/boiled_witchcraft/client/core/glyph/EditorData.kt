package kitowashere.boiled_witchcraft.client.core.glyph

import kitowashere.boiled_witchcraft.client.core.glyph.EditorData.SectionRenderer
import kitowashere.boiled_witchcraft.common.capabilities.handlers.glyph.GlyphEditorHandler
import net.minecraft.client.DeltaTracker
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics

typealias EditorRendererBuilder = GlyphEditorHandler.() -> SectionRenderer

object EditorData {

    /*val editorRendererBuilderRegistry: Registry<EditorRendererBuilder> =
        register.makeRegistry { RegistryBuilder(editorRendererBuilderKey) }*/

    /*val editorSectionRenderers by
        lazy { editorRendererBuilderRegistry.associate { editorRendererBuilderRegistry.getKey(it)!! to it!! } }*/

    data class SectionRenderer(val renderer: (gui: GuiGraphics, delta: DeltaTracker, font: Font, x: Int, y: Int) -> Unit,
                               val height: (Font) -> Int)
}