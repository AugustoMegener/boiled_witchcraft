package kitowashere.boiled_witchcraft.common.world.glyph.editor.option

@JvmInline
value class EditorOptionType<T : EditorOption<*, *>>(val supplier: () -> T)