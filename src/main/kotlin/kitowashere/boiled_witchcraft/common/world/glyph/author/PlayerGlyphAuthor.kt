package kitowashere.boiled_witchcraft.common.world.glyph.author

import io.kito.kore.common.data.Save
import io.kito.kore.common.data.codec.CodecSource
import io.kito.kore.common.data.codec.KMapCodecSerializer
import io.kito.kore.common.reflect.Scan
import io.kito.kore.util.minecraft.minecraftClient
import kitowashere.boiled_witchcraft.common.registry.DataAttachTypes.glyphCompositions
import kitowashere.boiled_witchcraft.common.registry.DataAttachTypes.unlockedGlyphs
import kitowashere.boiled_witchcraft.common.registry.GlyphAuthorTypeTypes.playerGlyphAuthor
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import net.minecraft.core.UUIDUtil
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.neoforged.neoforge.server.ServerLifecycleHooks
import java.util.*

@JvmInline
value class PlayerGlyphAuthor(@Save val uuid: UUID) : GlyphAuthor {

    val player get() = ServerLifecycleHooks.getCurrentServer()?.playerList?.getPlayer(uuid)

    override val type get() = playerGlyphAuthor

    override val compositions get() = player?.glyphCompositions ?: listOf()

    override val avaliableGlyphs get() = player?.unlockedGlyphs ?: listOf()
    override val level: Level get() = player?.level() ?: minecraftClient.level!!

    override fun addComposition(stack: GlyphStack) {
        if (player == null) return
        player!!.glyphCompositions += stack
    }

    override fun removeComposition(idx: Int) {
        if (player == null) return
        player!!.glyphCompositions = player!!.glyphCompositions.filterIndexed { i, _ -> i != idx }
    }

    @Scan
    companion object : KMapCodecSerializer<PlayerGlyphAuthor>(PlayerGlyphAuthor::class) {
        val Player.editorUser get() = PlayerGlyphAuthor(uuid)

        @CodecSource
        fun uuidCodec() = UUIDUtil.CODEC
    }
}