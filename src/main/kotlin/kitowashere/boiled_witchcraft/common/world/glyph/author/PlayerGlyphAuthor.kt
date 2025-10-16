package kitowashere.boiled_witchcraft.common.world.glyph.author

import io.kito.kore.common.data.Save
import io.kito.kore.common.data.codec.CodecSource
import io.kito.kore.common.data.codec.KMapCodecSerializer
import io.kito.kore.common.reflect.Scan
import kitowashere.boiled_witchcraft.common.registry.DataAttachTypes.glyphClipboard
import kitowashere.boiled_witchcraft.common.registry.DataAttachTypes.glyphCompositions
import kitowashere.boiled_witchcraft.common.registry.DataAttachTypes.unlockedGlyphs
import kitowashere.boiled_witchcraft.common.registry.GlyphAuthorTypes.playerGlyphAuthor
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import net.minecraft.client.Minecraft
import net.minecraft.core.UUIDUtil
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.neoforged.fml.LogicalSide
import net.neoforged.fml.util.thread.EffectiveSide
import net.neoforged.neoforge.server.ServerLifecycleHooks
import java.util.*


class PlayerGlyphAuthor(@Save val uuid: UUID) : GlyphAuthor {

    val player: Player? get() =
        when (EffectiveSide.get()!!) {
            LogicalSide.CLIENT -> Minecraft.getInstance().player?.takeIf { it.uuid == uuid }
            LogicalSide.SERVER -> ServerLifecycleHooks.getCurrentServer()?.playerList?.getPlayer(uuid)
        }

    override val type get() = playerGlyphAuthor

    override val compositions get() = player?.glyphCompositions ?: listOf()

    override var clipBoard: GlyphStack
        get() = player?.glyphClipboard ?: GlyphStack.empty
        set(value) { player?.glyphClipboard = value }

    override val avaliableGlyphs get() =
        player?.unlockedGlyphs ?: listOf()
    override val level: Level? get() = player?.level()

    override fun addComposition(stack: GlyphStack) {
        if (player == null) throw IllegalStateException("Invalid or inacessible player author")

        player!!.glyphCompositions += stack
    }

    override fun removeComposition(idx: Int) {
        if (player == null) throw IllegalStateException("Invalid or inacessible player author")
        if (idx !in compositions.indices) throw IllegalStateException("Index out of range")

        player!!.glyphCompositions = player!!.glyphCompositions.filterIndexed { i, _ -> i != idx }
    }

    @Scan
    companion object : KMapCodecSerializer<PlayerGlyphAuthor>(PlayerGlyphAuthor::class) {
        val Player.editorUser get() = PlayerGlyphAuthor(uuid)

        @CodecSource
        fun uuidCodec() = UUIDUtil.CODEC
    }
}