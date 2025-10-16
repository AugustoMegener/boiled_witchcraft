package kitowashere.boiled_witchcraft.common.registry

import io.kito.kore.common.reflect.Scan
import io.kito.kore.common.registry.SimpleRegister
import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.registry.Registries.glyphAuthorTypeRegistryKey
import kitowashere.boiled_witchcraft.common.world.glyph.author.GlyphAuthorType
import kitowashere.boiled_witchcraft.common.world.glyph.author.PlayerGlyphAuthor
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

@Scan
object GlyphAuthorTypes : SimpleRegister<GlyphAuthorType<*>>(ID, glyphAuthorTypeRegistryKey) {

    val playerGlyphAuthor by "player_glyph_author" { GlyphAuthorType(PlayerGlyphAuthor.mapCodec) }
}