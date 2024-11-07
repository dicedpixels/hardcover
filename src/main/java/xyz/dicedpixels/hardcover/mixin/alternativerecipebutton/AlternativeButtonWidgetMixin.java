package xyz.dicedpixels.hardcover.mixin.alternativerecipebutton;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget.AlternativeButtonWidget.InputSlot;
import net.minecraft.client.gui.tooltip.HoveredTooltipPositioner;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.NetworkRecipeId;
import net.minecraft.text.Text;

import xyz.dicedpixels.hardcover.Config;
import xyz.dicedpixels.hardcover.Textures;
import xyz.dicedpixels.hardcover.gui.RecipeTooltipComponent;
import xyz.dicedpixels.hardcover.interfaces.IRecipeResults;
import xyz.dicedpixels.hardcover.mixin.accessors.DrawContextAccessor;
import xyz.dicedpixels.hardcover.mixin.accessors.RecipeAlternativesWidgetAccessor;

@Mixin(RecipeAlternativesWidget.AlternativeButtonWidget.class)
abstract class AlternativeButtonWidgetMixin extends ClickableWidget {
    @Shadow(aliases = "field_3113")
    RecipeAlternativesWidget parent;

    @Unique
    ItemStack resultStack;

    @Shadow
    @Final
    private boolean craftable;

    @Shadow
    @Final
    private List<InputSlot> inputSlots;

    private AlternativeButtonWidgetMixin(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @Inject(method = "renderWidget", at = @At("HEAD"), cancellable = true)
    private void hardcover$renderAlternativeButton(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
        var client = MinecraftClient.getInstance();

        if (Config.instance().alternativeRecipeButton && client != null && client.world != null && client.player != null) {
            if (isHovered()) {
                var currentIndex = ((RecipeAlternativesWidgetAccessor) parent).hardcover$currentIndexProvider().currentIndex();
                ((DrawContextAccessor) context).hardcover$drawTooltip(client.textRenderer, List.of(new RecipeTooltipComponent(inputSlots, currentIndex)), mouseX, mouseY, HoveredTooltipPositioner.INSTANCE, null);
            }

            var texture = craftable ? (isSelected() ? Textures.WIDGET_SELECTED.get() : Textures.WIDGET.get()) : (isSelected() ? Textures.WIDGET_DISABLED_SELECTED.get() : Textures.WIDGET_DISABLED.get());

            context.drawGuiTexture(RenderLayer::getGuiTextured, texture, getX(), getY(), width, height);
            context.getMatrices().push();
            context.getMatrices().translate(0, 0, -35);
            context.drawItem(resultStack, getX() + 4, getY() + 4);
            context.getMatrices().pop();

            callbackInfo.cancel();
        }
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void hardcover$setResultStack(RecipeAlternativesWidget recipeAlternativesWidget, int x, int y, NetworkRecipeId recipeId, boolean craftable, List<InputSlot> inputSlots, CallbackInfo callbackInfo) {
        resultStack = ((IRecipeResults) parent).hardcover$getRecipeResults().get(recipeId);
    }
}
