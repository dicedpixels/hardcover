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
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget.AlternativeButtonWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget.AlternativeButtonWidget.InputSlot;
import net.minecraft.client.gui.tooltip.HoveredTooltipPositioner;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.NetworkRecipeId;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.contract.RecipeResultsProvider;
import xyz.dicedpixels.hardcover.gui.RecipeTooltipComponent;
import xyz.dicedpixels.hardcover.gui.Textures;
import xyz.dicedpixels.hardcover.mixin.accessors.RecipeAlternativesWidgetAccessor;
import xyz.dicedpixels.hardcover.mixin.invokers.DrawContextInvoker;

@Mixin(AlternativeButtonWidget.class)
abstract class AlternativeButtonWidgetMixin extends ClickableWidget {
    @Unique
    ItemStack hardcover$resultStack = ItemStack.EMPTY;

    @Shadow(aliases = "field_3113")
    RecipeAlternativesWidget parent;

    @Shadow
    @Final
    private boolean craftable;

    @Shadow
    @Final
    private List<InputSlot> inputSlots;

    public AlternativeButtonWidgetMixin(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @Unique
    private Identifier hardcover$getWidgetTexture() {
        if (craftable) {
            return Textures.CRAFTING_OVERLAY.getTexture(isSelected());
        } else {
            return Textures.CRAFTING_OVERLAY_DISABLED.getTexture(isSelected());
        }
    }

    @Inject(method = "renderWidget", at = @At("HEAD"), cancellable = true)
    private void hardcover$renderAlternativeButton(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo callbackInfo) {
        if (Configs.alternativeRecipeButton.getValue()) {
            var client = MinecraftClient.getInstance();

            if (client != null) {
                if (isHovered()) {
                    var currentIndex = ((RecipeAlternativesWidgetAccessor) parent).hardcover$getCurrentIndexProvider().currentIndex();

                    ((DrawContextInvoker) context).hardcover$drawTooltip(client.textRenderer, RecipeTooltipComponent.asList(inputSlots, currentIndex), mouseX, mouseY, HoveredTooltipPositioner.INSTANCE, null, false);
                }

                context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, hardcover$getWidgetTexture(), getX(), getY(), width, height);
                context.drawItem(hardcover$resultStack, getX() + 4, getY() + 4);

                callbackInfo.cancel();
            }
        }
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void hardcover$setResultStack(RecipeAlternativesWidget recipeAlternativesWidget, int x, int y, NetworkRecipeId recipeId, boolean craftable, List<InputSlot> inputSlots, CallbackInfo callbackInfo) {
        hardcover$resultStack = ((RecipeResultsProvider) parent).hardcover$getRecipeResults().get(recipeId);
    }
}
