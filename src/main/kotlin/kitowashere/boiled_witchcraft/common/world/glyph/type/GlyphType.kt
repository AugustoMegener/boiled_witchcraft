package kitowashere.boiled_witchcraft.common.world.glyph.type

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.primaries
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType.GlyphKind
import net.minecraft.core.Registry
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceKey.createRegistryKey
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.registries.DeferredRegister

/**
 * Class representing a type of glyph.
 *
 * this class is used to define glyph types, which is registered by a [DeferredRegister].
 * This class contains events that can be executed by external classes that represent it, thus defining common actions.
 *
 * For the purposes of this class, consider "instance" to be any object that uses a [GlyphData].
 *
 * @param kind The [GlyphKind] of this glyph.
 * @param sizes List of possible sizes for the glyph considering its side as a square.
 *
 * @see GlyphData
 */
abstract class GlyphType(val kind: GlyphKind, val sizes: List<Int>) {

    abstract fun newData(): GlyphData
    fun newData(nbt: CompoundTag) = newData().deserializeNBT(nbt)

    fun default() = Glyph(this)

    companion object {
        val placeholder: GlyphType; get() = primaries[0]

        val registryKey: ResourceKey<Registry<GlyphType>> =
            createRegistryKey(ResourceLocation("glyph_types"))
    }

    enum class GlyphKind {
        PRIMARY,
        STRUCTURAL,
        COMPOUND
    }
}