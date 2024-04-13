package kitowashere.boiled_witchcraft.client.keybinding

import com.mojang.blaze3d.platform.InputConstants
import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.caps.Caps.Entity.glyphEditor
import kitowashere.boiled_witchcraft.common.network.PGEPacket
import kitowashere.boiled_witchcraft.common.tags.ItemTags.glyphEditorTag
import kitowashere.boiled_witchcraft.common.util.WrapWay
import net.minecraft.client.KeyMapping
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.client.settings.KeyConflictContext

object Keybinding {

    val isEnabledInput = HashMap<KeyMapping, (LocalPlayer) -> Boolean>()
    val clientActionInput = HashMap<KeyMapping, (LocalPlayer) -> Unit>()
    val syncPacketInput = HashMap<KeyMapping, (LocalPlayer) -> CustomPacketPayload>()

    val keyMappings = ArrayList<KeyMapping>()
    private val category = "key.categories.$ID"

    object WrapKeys {
        private val isEnabled = { p: LocalPlayer -> p.handSlots.any { it.`is`(glyphEditorTag) } }
        private val packet = { w: WrapWay, e: Boolean -> PGEPacket(w, e) }

        val selectorNext =
            newKeyMapping("selector_next", InputConstants.KEY_UP, getEditor(false, WrapWay.NEXT))
        val selectorPrior =
            newKeyMapping("selector_prior", InputConstants.KEY_DOWN, getEditor(false, WrapWay.PRIOR))
        val editorNext =
            newKeyMapping("editor_next", InputConstants.KEY_RIGHT, getEditor(true, WrapWay.NEXT))
        val editorPrior =
            newKeyMapping("editor_prior", InputConstants.KEY_LEFT, getEditor(true, WrapWay.PRIOR))

        private fun getEditor(isEditor: Boolean, way: WrapWay): KeyMapBuilder.() -> Unit = {
            isEnabled(isEnabled)
            clientAction { when (isEditor) { true   -> it.glyphEditor.editor::edit
                                             false  -> it.glyphEditor.editor::wrap }(way)

            it.sendSystemMessage(it.glyphEditor.editor.info) }
            syncPacket { _ -> packet(way, isEditor) }
        }
    }

    private fun newKeyMapping(description: String, input: Int, builder: KeyMapBuilder.() -> Unit) =
        KeyMapBuilder(KeyMapping("key.$ID.$description",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(input, -1),
            category)
            .also { keyMappings + it }).also(builder).build()

    class KeyMapBuilder(private val keyMapping: KeyMapping) {

        fun isEnabled(predicate: (LocalPlayer) -> Boolean) { isEnabledInput[keyMapping] = predicate }
        fun clientAction(action: (LocalPlayer) -> Unit) { clientActionInput[keyMapping] = action }
        fun syncPacket(builder: (LocalPlayer) -> CustomPacketPayload) {  syncPacketInput[keyMapping] = builder }

        fun build() = keyMapping
    }
}