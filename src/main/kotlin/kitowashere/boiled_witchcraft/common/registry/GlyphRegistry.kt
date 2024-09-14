package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.common.registry.GlyphReg.glyphRegistry
import kitowashere.boiled_witchcraft.common.registry.GlyphReg.glyphs
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory.Companion.onCategories
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory.Companion.primaries
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory.Companion.structurals
import kitowashere.boiled_witchcraft.common.world.glyph.NoneGlyph
import kitowashere.boiled_witchcraft.common.world.glyph.RingGlyph
import kitowashere.boiled_witchcraft.common.world.glyph.type.*
import net.minecraft.resources.ResourceLocation.parse
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object GlyphRegistry : Register<Glyph>(glyphs) {

    val noneGlyph by "none_glyph" by { NoneGlyph }

    val fireGlyph  by "fire_glyph"  by { FireGlyph .onCategories(primaries) }
    val iceGlyph   by "ice_glyph"   by { IceGlyph  .onCategories(primaries) }
    val plantGlyph by "plant_glyph" by { PlantGlyph.onCategories(primaries) }
    val lightGlyph by "light_glyph" by { LightGlyph.onCategories(primaries) }

    val ringGlyph by "ring_glyph" by { RingGlyph.onCategories(structurals) }


    object Util {
        fun glyphFromID(id: String) = glyphRegistry[parse(id)]

        val Glyph.id get() = glyphRegistry.getKey(this)!!.toString()

        fun getGlyphLocation(glyphType: Glyph) = glyphRegistry.getKey(glyphType)!!
    }
}