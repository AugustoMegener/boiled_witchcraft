package kitowashere.boiled_witchcraft.common.world.glyph.type

import kitowashere.boiled_witchcraft.common.data.glyph.PillarGlyphData
import net.minecraft.world.entity.player.Player

class FireGlyphType : GlyphType<PillarGlyphData>(GlyphKind.PRIMARY, { PillarGlyphData() }) {
    override val blockInteracted = emptyEvent<BlockEventCtx>() //templateEvent(surfacedPillar, TODO("Not yet implemented"))

    override val itemPutted: GlyphEvent<BlockEventCtx> = emptyEvent()
    override val itemUsed: GlyphEvent<Player?> = emptyEvent()
}