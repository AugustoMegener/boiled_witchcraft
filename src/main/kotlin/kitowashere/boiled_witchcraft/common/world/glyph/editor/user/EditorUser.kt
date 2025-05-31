package kitowashere.boiled_witchcraft.common.world.glyph.editor.user

import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import net.minecraft.world.level.Level

interface EditorUser {
    val avaliableGlyphs: List<Glyph<*>>
    val level: Level
}