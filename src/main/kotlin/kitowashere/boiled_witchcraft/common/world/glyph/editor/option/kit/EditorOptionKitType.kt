package kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit

@JvmInline
value class EditorOptionKitType<T : EditorOptionKit<*>>(val supplier: (GlyphToEditKind) -> T) {

    companion object {
        @Suppress
        fun EditorOptionKitType<*>.newFrom(glyphToEditKind: GlyphToEditKind) = supplier(glyphToEditKind)
    }
}