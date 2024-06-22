package kitowashere.boiled_witchcraft.common.registry

import com.mojang.serialization.Codec
import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.Util.id
import kitowashere.boiled_witchcraft.common.world.glyph.FireGlyph
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory.Companion.onCategories
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory.Companion.primaries
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory.Companion.structurals
import kitowashere.boiled_witchcraft.common.world.glyph.NoneGlyph
import kitowashere.boiled_witchcraft.common.world.glyph.RingGlyph
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation.parse
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.RegistryBuilder

object GlyphRegistry {

    private val registryKey: ResourceKey<Registry<Glyph>> =
        ResourceKey.createRegistryKey(parse("$ID:glyphs"))

    val glyphRegistry: DeferredRegister<Glyph> = DeferredRegister.create(registryKey, ID)


    val noneGlyph: DeferredHolder<Glyph, NoneGlyph> =
        glyphRegistry.register("none_glyph") { -> NoneGlyph.onCategories(primaries) }

    val fireGlyph: DeferredHolder<Glyph, FireGlyph> =
        glyphRegistry.register("fire_glyph") { -> FireGlyph.onCategories(primaries) }

    val ringGlyph: DeferredHolder<Glyph, RingGlyph> =
        glyphRegistry.register("ring_glyph") { -> RingGlyph.onCategories(structurals) }


    val glyphs: Registry<Glyph> = glyphRegistry.makeRegistry { RegistryBuilder(registryKey) }

    object Util {
        fun glyphFromID(id: String) = glyphs[parse(id)]

        val Glyph.id get() = glyphs.getKey(this)!!.toString()

        fun getGlyphLocation(glyphType: Glyph) = glyphs.getKey(glyphType)!!
    }
}