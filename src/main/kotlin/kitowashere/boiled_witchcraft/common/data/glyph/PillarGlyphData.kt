package kitowashere.boiled_witchcraft.common.data.glyph

import io.kito.kore.common.data.Save
import io.kito.kore.common.data.codec.KMapCodecSerializer
import io.kito.kore.common.reflect.Scan
import kitowashere.boiled_witchcraft.common.world.glyph.PillarGlyph

open class PillarGlyphData(type: PillarGlyph<*>) : GlyphData(type) {

    @Save
    var height = 1

    @Scan
    companion object : KMapCodecSerializer<PillarGlyphData>(PillarGlyphData::class)
}