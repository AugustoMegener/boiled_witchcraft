package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.type.Glyph
import net.minecraft.world.entity.player.Player
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.registries.NeoForgeRegistries.ATTACHMENT_TYPES


object AttachRegistry : Register<AttachmentType<*>>(ATTACHMENT_TYPES) {

    val glyphStackAttach = "glyph_stack" by
            { AttachmentType.builder { -> GlyphStack(Glyph.placeholder) } .serialize(GlyphStack.codec) .build() }

    var Player.glyphStack: GlyphStack
        get() = getData(glyphStackAttach)
        set(value) { setData(glyphStackAttach, value) }
}