package kitowashere.boiled_witchcraft.common.data.glyph

import io.kito.kore.common.data.Save
import io.kito.kore.common.data.codec.KMapCodecSerializer
import io.kito.kore.common.reflect.Scan
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph

open class GlyphData(@Save var type: Glyph<*>) {

    @Save
    var size = type.sizes.min()


    @Scan
    companion object : KMapCodecSerializer<GlyphData>(GlyphData::class)
}
