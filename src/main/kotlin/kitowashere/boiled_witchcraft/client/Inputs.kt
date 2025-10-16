package kitowashere.boiled_witchcraft.client

import com.mojang.blaze3d.platform.InputConstants
import io.kito.kore.client.InputRegistry
import io.kito.kore.client.RegisterInput
import io.kito.kore.common.event.KSubscribe
import io.kito.kore.common.reflect.Scan
import io.kito.kore.util.minecraft.keySysMain
import io.kito.kore.util.minecraft.minecraftClient
import kitowashere.boiled_witchcraft.BoiledWitchcraft.keyCategoryLocale
import kitowashere.boiled_witchcraft.BoiledWitchcraft.keyLocale
import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.client.ClientData.isEditingGlyph
import kitowashere.boiled_witchcraft.client.gui.screen.GlyphPlacingScreen
import kitowashere.boiled_witchcraft.common.network.packet.*
import kitowashere.boiled_witchcraft.common.registry.DataAttachTypes.glyphEditor
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.GlyphPlacementInput
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.SelectorInput
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.UseInput
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit.GlyphToEditKind
import net.minecraft.client.KeyMapping
import net.minecraft.client.player.LocalPlayer
import net.neoforged.api.distmarker.Dist.CLIENT
import net.neoforged.neoforge.event.tick.PlayerTickEvent

@Scan
object Inputs {

    @RegisterInput
    val enableEditing by InputRegistry {
        KeyMapping(keyLocale("enable_editing_glyph"), keySysMain, InputConstants.KEY_G, keyCategoryLocale(ID))
    } runs { isEditingGlyph = !isEditingGlyph }


    @RegisterInput
    val nextGlyph by InputRegistry {
        KeyMapping(keyLocale("next_glyph"), keySysMain, InputConstants.KEY_COMMA, keyCategoryLocale(ID))
    } syncs { SelectGlyphPacket(1) } justWhen ::isEditingGlyph

    @RegisterInput
    val prevGlyph by InputRegistry {
        KeyMapping(keyLocale("prev_glyph"), keySysMain, InputConstants.KEY_PERIOD, keyCategoryLocale(ID))
    } syncs { SelectGlyphPacket(-1) } justWhen ::isEditingGlyph

    @RegisterInput
    val nextOption by InputRegistry {
        KeyMapping(keyLocale("next_option"), keySysMain, InputConstants.KEY_DOWN, keyCategoryLocale(ID))
    } syncs { SelectOptionPacket(1) } justWhen ::isEditingGlyph

    @RegisterInput
    val prevOption by InputRegistry {
        KeyMapping(keyLocale("prev_option"), keySysMain, InputConstants.KEY_UP, keyCategoryLocale(ID))
    } syncs { SelectOptionPacket(-1) } justWhen ::isEditingGlyph


    @RegisterInput
    val selectorInputNext by InputRegistry {
        KeyMapping(keyLocale("select_next"), keySysMain, InputConstants.KEY_RIGHT, keyCategoryLocale(ID))
    } syncs { SelectorInputPacket(1) } justWhen {
        isEditingGlyph && minecraftClient.player?.glyphEditor?.acceptsInputOf(SelectorInput::class) == true
    }


    @RegisterInput
    val selectorInputPrev by InputRegistry {
        KeyMapping(keyLocale("select_prev"), keySysMain, InputConstants.KEY_LEFT, keyCategoryLocale(ID))
    } syncs { SelectorInputPacket(-1) } justWhen {
        isEditingGlyph && minecraftClient.player?.glyphEditor?.acceptsInputOf(SelectorInput::class) == true
    }

    @RegisterInput
    val useInput by InputRegistry {
        KeyMapping(keyLocale("use_input"), keySysMain, InputConstants.KEY_RETURN, keyCategoryLocale(ID))
    } syncs ::UseInputPacket justWhen {
        isEditingGlyph && minecraftClient.player?.glyphEditor?.acceptsInputOf(UseInput::class) == true
    }

    @RegisterInput
    val composeGlyph by InputRegistry {
        KeyMapping(keyLocale("compose_glyph"), keySysMain, InputConstants.KEY_SEMICOLON, keyCategoryLocale(ID))
    } syncs ::ComposeGlyphPacket justWhen {
        isEditingGlyph && minecraftClient.player!!.glyphEditor.let {
            it.options.glyphToEditKind == GlyphToEditKind.SOURCE && it.stack.glyph.isLinkable
        }
    }

    @KSubscribe([CLIENT])
    fun PlayerTickEvent.Post.playerTickEvent() {
        val player = entity as? LocalPlayer ?: return

        if (isEditingGlyph && player.glyphEditor.acceptsInputOf(GlyphPlacementInput::class) && useInput.consumeClick())
        {
            minecraftClient.setScreen(GlyphPlacingScreen())
        }
    }
}