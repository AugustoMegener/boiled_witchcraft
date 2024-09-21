package kitowashere.boiled_witchcraft.client.keymapping

import com.mojang.blaze3d.platform.InputConstants
import kitowashere.boiled_witchcraft.common.capabilities.Caps.Entity.glyphEditor
import kitowashere.boiled_witchcraft.common.network.PGEPacket
import kitowashere.boiled_witchcraft.common.util.GlyphUtil.canWriteGlyphOn
import kitowashere.boiled_witchcraft.common.util.WrapWay

object WrapKeys : KeyRegister() {

    override val keyBuilder: Keymapping.() -> Unit = {
        new("selector_next",    InputConstants.KEY_DOWN,    getEditor(false, WrapWay.NEXT ))
        new("selector_prior",   InputConstants.KEY_UP,      getEditor(false, WrapWay.PRIOR))
        new("editor_next",      InputConstants.KEY_RIGHT,   getEditor(true,  WrapWay.NEXT ))
        new("editor_prior",     InputConstants.KEY_LEFT,    getEditor(true,  WrapWay.PRIOR))
    }

    private fun getEditor(isEditor: Boolean, way: WrapWay): Keymapping.KeyMapBuilder.() -> Unit = {
        isEnabled { it.canWriteGlyphOn != null }

        clientAction { with(it.glyphEditor.editor) { (if (isEditor) value::wrap else ::wrap)(way) } }

        syncPacket { _ -> PGEPacket(way, isEditor) }
    }
}