package kitowashere.boiled_witchcraft.client.keymapping

import com.mojang.blaze3d.platform.InputConstants
import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import net.minecraft.client.KeyMapping
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.client.settings.KeyConflictContext

object Keymapping {

    val isEnabledInput = HashMap<KeyMapping, (LocalPlayer) -> Boolean>()
    val clientActionInput = HashMap<KeyMapping, (LocalPlayer) -> Unit>()
    val syncPacketInput = HashMap<KeyMapping, (LocalPlayer) -> CustomPacketPayload>()

    internal val keyBuilders = ArrayList<() -> Unit>()
    internal val keyMappings = ArrayList<Lazy<KeyMapping>>()
    private val category = "key.categories.$ID"


    operator fun invoke(description: String, input: Int, builder: KeyMapBuilder.() -> Unit) =
        KeyMapBuilder(KeyMapping("key.$ID.$description", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, input, category))
            .also(builder).build()

    fun new(description: String, input: Int, builder: KeyMapBuilder.() -> Unit) =
            KeyMapBuilder(KeyMapping("key.$ID.$description", KeyConflictContext.IN_GAME,
                                            InputConstants.Type.KEYSYM, input, category))
                         .also(builder).build().register()

    fun KeyMapping.register() = this.also { keyMappings.add(lazy { it }) }

    class KeyMapBuilder(private val keyMapping: KeyMapping) {

        fun isEnabled(predicate: (LocalPlayer) -> Boolean) { isEnabledInput[keyMapping] = predicate }
        fun clientAction(action: (LocalPlayer) -> Unit) { clientActionInput[keyMapping] = action }
        fun syncPacket(builder: (LocalPlayer) -> CustomPacketPayload) {  syncPacketInput[keyMapping] = builder }

        fun build() = keyMapping
    }
}