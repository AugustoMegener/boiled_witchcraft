package kitowashere.boiled_witchcraft.common.data.glyph

import net.minecraft.world.entity.LivingEntity

class PillarGlyphData : GlyphData() {

    var height by DataField(0)

    override val applyOnBlock = builder.build<LivingEntity?>(GlyphEvent<LivingEntity?> { handler, entity ->
    })
    override val glyphItemUsed = GlyphEvent<LivingEntity?> { handler, entity ->
    }

    override val blockStepped=  GlyphEvent<LivingEntity> { handler, entity ->
    }
    override val blockClicked = GlyphEvent<LivingEntity> { handler, entity ->
    }
}