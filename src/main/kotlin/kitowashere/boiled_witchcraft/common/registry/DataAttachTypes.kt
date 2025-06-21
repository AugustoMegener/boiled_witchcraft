package kitowashere.boiled_witchcraft.common.registry

import io.kito.kore.common.reflect.Scan
import io.kito.kore.common.registry.SimpleRegister
import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.registry.Registries.glyphRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph.Companion.glyphCodec
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.author.PlayerGlyphAuthor.Companion.editorUser
import kitowashere.boiled_witchcraft.common.world.glyph.editor.GlyphEditor
import net.minecraft.world.entity.player.Player
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.registries.NeoForgeRegistries
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

@Scan
object DataAttachTypes : SimpleRegister<AttachmentType<*>>(ID, NeoForgeRegistries.ATTACHMENT_TYPES) {

    val unlokcedGlyphs: AttachmentType<List<Glyph<*>>> by "unlocked_glyphs" {
        AttachmentType.builder { -> listOf<Glyph<*>>() }.serialize(glyphCodec().listOf()).build()
    }

    var Player.unlockedGlyphs: List<Glyph<*>>
        set(value) { setData(unlokcedGlyphs, value) }
        get() =
            if (isCreative) glyphRegistry.distinct().filter { it.isPrimary } else getData(unlokcedGlyphs)

    val playerGlyphEditorAttach: AttachmentType<GlyphEditor> by "player_glyph_editor" {
        AttachmentType.serializable { it -> GlyphEditor((it as Player).editorUser) }.build()
    }

    var Player.glyphEditor: GlyphEditor
        set(value) { setData(playerGlyphEditorAttach, value) }
        get() = getData(playerGlyphEditorAttach)

    val glyphCompositionsAttach: AttachmentType<List<GlyphStack>> by "glyph_compositions" {
        AttachmentType.builder { -> listOf<GlyphStack>() }.serialize(GlyphStack.codec.listOf()).build()
    }

    var Player.glyphCompositions: List<GlyphStack>
        get() =  getData(glyphCompositionsAttach)
        set(value) { setData(glyphCompositionsAttach, value) }

    val glyphClipboardAttach: AttachmentType<GlyphStack> by "glyph_clipboard" {
        AttachmentType.builder { -> GlyphStack.empty }.serialize(GlyphStack.codec).build()
    }

    var Player.glyphClipboard: GlyphStack
        get() = getData(glyphClipboardAttach)
        set(value) { setData(glyphClipboardAttach, value) }
}