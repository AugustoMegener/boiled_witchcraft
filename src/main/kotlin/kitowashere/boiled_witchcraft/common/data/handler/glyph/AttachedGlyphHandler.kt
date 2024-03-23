package kitowashere.boiled_witchcraft.common.data.handler.glyph

import kitowashere.boiled_witchcraft.common.registry.AttachRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.attachment.IAttachmentHolder

open class AttachedGlyphHandler(private val holder: IAttachmentHolder) : IGlyphHandler {
    private val glyphAttach = AttachRegistry.attachRegistry.register("glyph") { ->
        AttachmentType.serializable { -> Glyph() } .build()
    }

    override var glyph: Glyph = holder.getData(glyphAttach)

    override fun onChanged() {
        holder.setData(glyphAttach, glyph)
    }
}