package kitowashere.boiled_witchcraft.common.registry

import io.kito.kore.common.reflect.Scan
import io.kito.kore.common.registry.SimpleRegister
import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.registry.Registries.glyphRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.EmptyGlyph
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph.Companion.glyphCodec
import kitowashere.boiled_witchcraft.common.world.glyph.editor.GlyphEditor
import kitowashere.boiled_witchcraft.common.world.glyph.editor.user.PlayerEditorUser.Companion.editorUser
import net.minecraft.world.entity.player.Player
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.registries.NeoForgeRegistries
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

@Scan
object DataAttachTypes : SimpleRegister<AttachmentType<*>>(ID, NeoForgeRegistries.ATTACHMENT_TYPES) {

    val unlokcedGlyphs by "unlocked_glyphs" {
        AttachmentType.builder { -> listOf<Glyph<*>>() }.serialize(glyphCodec().listOf()).build()
    }

    var Player.unlockedGlyphs: List<Glyph<*>>
        set(value) { setData(unlokcedGlyphs, value) }
        get() = if (isCreative) glyphRegistry.toList().filter { it != EmptyGlyph } else getData(unlokcedGlyphs)

    val glyphEditorAttach: AttachmentType<GlyphEditor?> by "glyph_editor" {
        AttachmentType.builder<GlyphEditor?> { -> null }.serialize(GlyphEditor.codec).build()
    }

    var Player.glyphEditor: GlyphEditor
        set(value) { setData(glyphEditorAttach, value) }
        get() = getData(glyphEditorAttach) ?: run { GlyphEditor(editorUser).also { setData(glyphEditorAttach, it) } }
}