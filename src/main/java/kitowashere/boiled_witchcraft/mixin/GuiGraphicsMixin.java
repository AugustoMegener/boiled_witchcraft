package kitowashere.boiled_witchcraft.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import kitowashere.boiled_witchcraft.client.render.gui.inventory.tooltip.ClientGlyphStackTooltip;
import kitowashere.boiled_witchcraft.common.resource.CanvasRegistry;
import kitowashere.boiled_witchcraft.common.resource.mosh.MohsRegistry;
import kitowashere.boiled_witchcraft.common.util.GlyphUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Mixin(GuiGraphics.class)
public class GuiGraphicsMixin {

    // TODO: remove this and implement with RenderTooltipEvent
    @WrapOperation(
            method = "renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/neoforged/neoforge/client/ClientHooks;gatherTooltipComponents(Lnet/minecraft/world/item/ItemStack;Ljava/util/List;Ljava/util/Optional;IIILnet/minecraft/client/gui/Font;)Ljava/util/List;"
            )
    )
    public List<ClientTooltipComponent> renderTooltip(ItemStack stack,
                                                      List<? extends FormattedText> textElements,
                                                      Optional<TooltipComponent> itemComponent,
                                                      int mouseX,
                                                      int screenWidth,
                                                      int screenHeight,
                                                      Font fallbackFont,
                                                      Operation<List<ClientTooltipComponent>> original)
    {
        var txt = new ArrayList<FormattedText>(textElements);

        var mohs = MohsRegistry.ItemMohs.INSTANCE.getMohs(stack);

        if (mohs != null)
            txt.add(Component.literal(mohs + "\uD835\uDCF1\uD835\uDCF6")
                             .withStyle(Style.EMPTY.withColor(0x575757)));

        var list = new ArrayList<>(
                original.call(stack, txt, itemComponent, mouseX, screenWidth, screenHeight, fallbackFont)
        );

        var glyphCanvas = CanvasRegistry.ItemCanvas.INSTANCE;
        var canvas = glyphCanvas.getGlyphCanvas(stack);

        if (canvas != null) list.add(new ClientGlyphStackTooltip(canvas, Objects.requireNonNull(GlyphUtil.INSTANCE.getGlyphStack(stack))));

        return list;
    }
}
