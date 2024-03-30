package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries


object AttachRegistry {
    val attachRegistry: DeferredRegister<AttachmentType<*>> =
        DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ID)

    val glyphStackAttach: DeferredHolder<AttachmentType<*>, AttachmentType<GlyphStack>> =
        attachRegistry.register("glyph_stack") { -> AttachmentType.serializable { -> GlyphStack() }.build() }
}


