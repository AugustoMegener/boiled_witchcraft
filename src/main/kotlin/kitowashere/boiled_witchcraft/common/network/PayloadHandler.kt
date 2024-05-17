package kitowashere.boiled_witchcraft.common.network

import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.network.handling.IPayloadHandler

abstract class PayloadHandler<T : CustomPacketPayload> : IPayloadHandler<T> {

    abstract val type:  CustomPacketPayload.Type<T>
    abstract val codec: StreamCodec<RegistryFriendlyByteBuf, T>
}