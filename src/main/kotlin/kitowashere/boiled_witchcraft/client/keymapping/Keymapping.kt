package kitowashere.boiled_witchcraft.client.keymapping

import com.mojang.blaze3d.platform.InputConstants
import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import net.minecraft.client.KeyMapping
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.client.settings.KeyConflictContext

typealias IsEnabledInput    = (LocalPlayer) -> Boolean
typealias ClientActionInput = (LocalPlayer) -> Unit
typealias SyncPacketInput   = (LocalPlayer) -> CustomPacketPayload

object Keymapping {

    private const val CATEGORY = "key.categories.$ID"

    internal val keyMappings = ArrayList<KeyMapping>()
    val inputsData = HashMap<KeyMapping, InputData>()

    fun new(description: String, input: Int, builder: KeyMapBuilder.() -> Unit) =
        KeyMapBuilder(
            KeyMapping(
                "key.$ID.$description", KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM, input, CATEGORY
            )
        ).also(builder).register()


    class KeyMapBuilder(private val keyMapping: KeyMapping) {
        private var isEnable:       IsEnabledInput?     = null
        private var clientAction:   ClientActionInput?  = null
        private var syncPacket:     SyncPacketInput?    = null

        fun isEnabled(predicate: IsEnabledInput)    { isEnable      = predicate }
        fun clientAction(action: ClientActionInput) { clientAction  = action    }
        fun syncPacket(builder: SyncPacketInput)    { syncPacket    = builder   }

        fun register() { keyMappings += keyMapping
                         inputsData[keyMapping] = InputData(isEnable, clientAction, syncPacket) }
    }

    data class InputData(val isEnabledInput:    IsEnabledInput?,
                         val clientActionInput: ClientActionInput?,
                         val syncPacketInput:   SyncPacketInput?)
}