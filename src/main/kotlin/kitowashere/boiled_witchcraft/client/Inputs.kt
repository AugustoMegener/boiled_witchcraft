package kitowashere.boiled_witchcraft.client

import com.mojang.blaze3d.platform.InputConstants
import io.kito.kore.client.InputRegistry
import io.kito.kore.client.RegisterInput
import io.kito.kore.common.reflect.Scan
import io.kito.kore.util.minecraft.keySysMain
import kitowashere.boiled_witchcraft.BoiledWitchcraft.keyCategoryLocale
import kitowashere.boiled_witchcraft.BoiledWitchcraft.keyLocale
import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.network.packet.SelectGlyphPacket
import net.minecraft.client.KeyMapping

@Scan
object Inputs {

    @RegisterInput
    val nextGlyph by InputRegistry {
        KeyMapping(keyLocale("next_glyph"), keySysMain, InputConstants.KEY_COMMA, keyCategoryLocale(ID))
    } syncs { SelectGlyphPacket(1) }

    @RegisterInput
    val prevGlyph by InputRegistry {
        KeyMapping(keyLocale("prev_glyph"), keySysMain, InputConstants.KEY_PERIOD, keyCategoryLocale(ID))
    } syncs { SelectGlyphPacket(-1) }
}