package kitowashere.boiled_witchcraft.common.data.glyph.editor

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.data.Caps
import kitowashere.boiled_witchcraft.common.data.util.WrapWay
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.GlyphGroup
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.translatableName
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.entity.player.Player


class PlayerGlyphEditor(player: Player) : GlyphEditor(player.getCapability(Caps.Glyph.entity)!!) {
    override val groups = arrayOf(GlyphGroup.primaries, GlyphGroup.structurals)

    private val wrappers = listOf(
        Wrapper(::wrapGroup) { Component.translatable("wrapper.$ID.group")
                                        .append(": ").append(group.name) },
        Wrapper(::wrapGlyph) { Component.translatable("wrapper.$ID.glyph")
                                        .append(": ").append(translatableName(glyph.type)) },
        Wrapper(::wrapField) { Component.translatable("wrapper.$ID.field")
                                        .append(": ").append(glyph.renderers[fieldIndex].name) },
        Wrapper(::editField) { glyph.renderers[fieldIndex].name.append(" > ")
                                    .append(glyph.renderers[fieldIndex].info) }
    )

    val info get() = wrappers[wrapperIndex].getInfo()

    data class Wrapper(val action: (WrapWay) -> Unit,
                       val getInfo: () -> MutableComponent)

    private var wrapperIndex = 0

    fun select(way: WrapWay) { wrapperIndex = getIndex(wrappers.size, wrapperIndex + way.value) }

    fun edit(way: WrapWay) { wrappers[wrapperIndex].action(way) }

    companion object {
        val Player.glyphEditor get() = PlayerGlyphEditor(this)
    }
}