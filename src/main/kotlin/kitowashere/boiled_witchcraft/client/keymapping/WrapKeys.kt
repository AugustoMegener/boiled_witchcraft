package kitowashere.boiled_witchcraft.client.keymapping

import com.mojang.blaze3d.platform.InputConstants
import kitowashere.boiled_witchcraft.common.caps.Caps.Entity.glyphEditor
import kitowashere.boiled_witchcraft.common.network.PGEPacket
import kitowashere.boiled_witchcraft.common.tags.ItemTags
import kitowashere.boiled_witchcraft.common.util.WrapWay
import net.minecraft.client.player.LocalPlayer

object WrapKeys : KeyRegister() {

    override val keyBuilder: Keymapping.() -> Unit = {
        new("selector_next",    InputConstants.KEY_UP,      getEditor(false, WrapWay.NEXT ))
        new("selector_prior",   InputConstants.KEY_DOWN,    getEditor(false, WrapWay.PRIOR))
        new("editor_next",      InputConstants.KEY_RIGHT,   getEditor(true,  WrapWay.NEXT ))
        new("editor_prior",     InputConstants.KEY_LEFT,    getEditor(true,  WrapWay.PRIOR))
    }

    private fun getEditor(isEditor: Boolean, way: WrapWay): Keymapping.KeyMapBuilder.() -> Unit = {
        isEnabled { p: LocalPlayer -> p.handSlots.any { ItemTags.glyphEditorTag in it.tags.toList() } }

        clientAction {
            when (isEditor) { true  -> it.glyphEditor.editor::edit
                              false -> it.glyphEditor.editor::wrap }(way)

            it.sendSystemMessage(it.glyphEditor.editor.info)
        }
        syncPacket { _ -> PGEPacket(way, isEditor) }
    }
}