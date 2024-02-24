package kitowashere.boiled_witchcraft.client.renderer

import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.builders.LayerDefinition

interface RegistrableLayer {
    val layerLocation: ModelLayerLocation

    fun createBodyLayer(): LayerDefinition
}