package kitowashere.boiled_witchcraft.common.network

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.network.handling.IPlayPayloadHandler

abstract class PlayPayloadHandler<T : CustomPacketPayload> : IPlayPayloadHandler<T>, FriendlyByteBuf.Reader<T> {
    abstract val id: ResourceLocation
}