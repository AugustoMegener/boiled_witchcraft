package kitowashere.boiled_witchcraft.common.registry

import io.kito.kore.common.reflect.Scan
import io.kito.kore.common.registry.SimpleRegister
import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.registry.Registries.glyphRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph.Companion.glyphCodec
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph.Companion.glyphStackStreamCodec
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph.Companion.glyphStreamCodec
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.author.PlayerGlyphAuthor.Companion.editorUser
import kitowashere.boiled_witchcraft.common.world.glyph.editor.GlyphEditor
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.world.entity.player.Player
import net.neoforged.neoforge.attachment.AttachmentSyncHandler
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.attachment.IAttachmentHolder
import net.neoforged.neoforge.registries.NeoForgeRegistries
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

@Scan
object DataAttachTypes : SimpleRegister<AttachmentType<*>>(ID, NeoForgeRegistries.ATTACHMENT_TYPES) {

    val unlokcedGlyphs: AttachmentType<List<Glyph<*>>> by "unlocked_glyphs" {
        AttachmentType.builder { -> listOf<Glyph<*>>() }
            .serialize(glyphCodec().listOf())
            .sync(ByteBufCodecs.collection(::ArrayList, glyphStreamCodec()))
            .build()
    }

    var Player.unlockedGlyphs: List<Glyph<*>>
        set(value) { setData(unlokcedGlyphs, value) }
        get() =
            if (isCreative) glyphRegistry.distinct().filter { it.isPrimary } else getData(unlokcedGlyphs)

    val playerGlyphEditorAttach: AttachmentType<GlyphEditor> by "player_glyph_editor" {
        AttachmentType.serializable { it -> GlyphEditor((it as Player).editorUser) }
            .sync(object : AttachmentSyncHandler<GlyphEditor> {
                override fun write(buf: RegistryFriendlyByteBuf, value: GlyphEditor, boolean: Boolean)
                    { buf.writeNbt(value.serializeNBT(buf.registryAccess())) }

                override fun read(holder: IAttachmentHolder, buf: RegistryFriendlyByteBuf, value: GlyphEditor?) =
                    holder.getData(playerGlyphEditorAttach)
                        .also { it.deserializeNBT(buf.registryAccess(), buf.readNbt()!!) }
            })
            .build()
    }

    var Player.glyphEditor: GlyphEditor
        set(value) { setData(playerGlyphEditorAttach, value) }
        get() = getData(playerGlyphEditorAttach)

    val glyphCompositionsAttach: AttachmentType<List<GlyphStack>> by "glyph_compositions" {
        AttachmentType.builder { -> listOf<GlyphStack>() }
            .serialize(GlyphStack.codec.listOf())
            .sync(ByteBufCodecs.collection(::ArrayList, glyphStackStreamCodec()))
            .build()
    }

    var Player.glyphCompositions: List<GlyphStack>
        get() =  getData(glyphCompositionsAttach)
        set(value) { setData(glyphCompositionsAttach, value) }

    val glyphClipboardAttach: AttachmentType<GlyphStack> by "glyph_clipboard" {
        AttachmentType.builder { -> GlyphStack.empty }
            .serialize(GlyphStack.codec)
            .sync(glyphStackStreamCodec())
            .build()
    }

    var Player.glyphClipboard: GlyphStack
        get() = getData(glyphClipboardAttach)
        set(value) { setData(glyphClipboardAttach, value) }
}