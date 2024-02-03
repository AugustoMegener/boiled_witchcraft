package kitowashere.boiled_witchcraft.client.gui.overlay

import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay

abstract class Overlay(namespace: String, path: String) : IGuiOverlay {
    val id = ResourceLocation(namespace, path)
}