package kitowashere.boiled_witchcraft.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import kitowashere.boiled_witchcraft.common.resource.canvas.ItemGlyphCanvas;
import kitowashere.boiled_witchcraft.common.world.inventory.tooltip.GlyphStackTooltip;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Optional;

@Mixin(GuiGraphics.class)
public class GuiGraphicsMixin {

    private static final ItemGlyphCanvas.Companion CANVAS = ItemGlyphCanvas.Companion;

    @ModifyArg(method = "renderTooltip(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;II)V",
               at = @At(value = "INVOKE",
                        target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V"),
               index = 2)
    public Optional<TooltipComponent> renderTooltip(Optional<TooltipComponent> component,
                                                    @Local(argsOnly = true) ItemStack stack)
    {
        return Optional.ofNullable(
               Optional.ofNullable(CANVAS.getGlyphCanvas(stack))
                       .map(c -> (TooltipComponent) new GlyphStackTooltip(c))
                       .orElse(component.orElse(null)));
    }


}
