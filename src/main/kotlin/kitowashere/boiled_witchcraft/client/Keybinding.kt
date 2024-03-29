package kitowashere.boiled_witchcraft.client

import com.mojang.blaze3d.platform.InputConstants
import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.data.glyph.editor.PlayerGlyphEditor.Companion.glyphEditor
import kitowashere.boiled_witchcraft.common.data.util.WrapWay
import kitowashere.boiled_witchcraft.common.data.util.WrapWay.*
import kitowashere.boiled_witchcraft.common.network.EditGlyphPacket
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.translatableName
import kitowashere.boiled_witchcraft.common.tags.ItemTags.glyphEditorTag
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientPacketListener
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.network.protocol.game.ServerGamePacketListener
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerGamePacketListenerImpl
import net.neoforged.neoforge.client.ClientHooks
import net.neoforged.neoforge.client.settings.KeyConflictContext
import net.neoforged.neoforge.network.PacketDistributor

object Keybinding {

    val isEnabledInput = HashMap<KeyMapping, (LocalPlayer) -> Boolean>()
    val clientActionInput = HashMap<KeyMapping, (LocalPlayer) -> Unit>()
    val syncPacketInput = HashMap<KeyMapping, (LocalPlayer) -> CustomPacketPayload>()

    val keyMappings = ArrayList<KeyMapping>()
    private val category = "key.categories.$ID"

    object WrapKeys {
        private val isEnabled = { p: LocalPlayer -> p.handSlots.any { it.tags.toList().contains(glyphEditorTag) } }
        private val syncPacket = { it: LocalPlayer -> EditGlyphPacket(it.glyphEditor.glyph) }

        val selectorNext =
            newKeyMapping("selector_next", InputConstants.KEY_UP, getEditor(false, NEXT))
        val selectorPrior =
            newKeyMapping("selector_prior", InputConstants.KEY_DOWN, getEditor(false, PRIOR))
        val editorNext =
            newKeyMapping("editor_next", InputConstants.KEY_RIGHT, getEditor(true, NEXT))
        val editorPrior =
            newKeyMapping("editor_prior", InputConstants.KEY_LEFT, getEditor(true, PRIOR))

        private fun getEditor(isEditor: Boolean, way: WrapWay): KeyMapBuilder.() -> Unit = {
            isEnabled(isEnabled)
            clientAction { when (isEditor) {true -> it.glyphEditor::edit; false -> it.glyphEditor::select }(way)
                           it.sendSystemMessage(it.glyphEditor.info) }
            syncPacket(syncPacket)
        }
    }

    private fun newKeyMapping(description: String, input: Int, builder: KeyMapBuilder.() -> Unit) =
        builder.invoke(KeyMapBuilder(KeyMapping("key.$ID.$description",
                                     KeyConflictContext.IN_GAME,
                                     InputConstants.getKey(input, -1),
                                     category)
                       .also { keyMappings + it }))

    class KeyMapBuilder(private val keyMapping: KeyMapping) {

        fun isEnabled(predicate: (LocalPlayer) -> Boolean) {
            isEnabledInput[keyMapping] = predicate
        }

        fun clientAction(action: (LocalPlayer) -> Unit) {
            clientActionInput[keyMapping] = action
        }

        fun syncPacket(packetBuilder: (LocalPlayer) -> CustomPacketPayload) {
            syncPacketInput[keyMapping] = packetBuilder
        }

        fun build() = keyMapping
    }
}