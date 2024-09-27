package kitowashere.boiled_witchcraft.client.event

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.core.glyph.EditorData
import kitowashere.boiled_witchcraft.client.keymapping.Keymapping
import kitowashere.boiled_witchcraft.client.render.atlas.GlyphAtlas.getSprite
import kitowashere.boiled_witchcraft.client.render.atlas.GlyphAtlas.sprite
import net.minecraft.client.Minecraft
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.ClientTickEvent
import net.minecraft.resources.ResourceLocation.parse as loc


@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = ID, value = [Dist.CLIENT])
object NeoForgedEventClient {

    @SubscribeEvent
    fun onClientTick(event: ClientTickEvent.Post) {
        val player = Minecraft.getInstance().player ?: return

        for (i in Keymapping.inputsData) {
            val data = i.value

            if (i.key.consumeClick() && data.isEnabledInput?.let { it(player) } == true) return

            data.clientActionInput?.let { it(player) }
            data.syncPacketInput  ?.let { it(player) }
        }
    }

    fun onRegisterSelectorRenderer(event: RegisterSelectorRendererEvent) {
        event.register(
            loc("$ID:category_selector") to {
                EditorData.SectionRenderer(
                    { gui, _, font, x, y ->
                        var yPos = y

                        for (i in categorySelect) {
                            var xPos = x

                            gui.drawString(
                                font, i.name, x, yPos, if (i == categorySelect.value) 0xeba434 else 0xffffff)
                            yPos += font.lineHeight + 2
                            for (ii in i.content) {
                                val size = 8

                                gui.blit(xPos, yPos, 0, size, size, ii.getSprite(ii.sizes[0]))

                                xPos += size + 2
                            }

                            yPos += 10
                        }

                    },
                    { f -> (f.lineHeight + 12) * (categorySelect.maxIndex + 1) }
                )
            },
            loc("$ID:glyph_selector") to {
                EditorData.SectionRenderer(
                    { gui, _, _, x, y ->
                        var xPos = x

                        for (i in glyphSelect) {
                            val size = 16

                            gui.blit(xPos, y + if (result.glyph == i) 0 else 10, 0, size, size, i.getSprite(i.sizes[0]))

                            xPos += size + 2
                        }
                    },
                    { _ -> 26 }
                )
            },
            loc("$ID:size") to {
                var height = 0

                EditorData.SectionRenderer(
                    { gui, _, font, x, y ->
                        val size = result.size

                        gui.drawCenteredString(font, "${size}:${size}", x + (size * 16) / 2, y, 0xffffff)
                        gui.blit(x, y + font.lineHeight + 2, 0, size * 16, size * 16, result.sprite)

                        height = font.lineHeight + 2 + size * 16
                    },
                    { height }
                )
            }
        )
    }
}