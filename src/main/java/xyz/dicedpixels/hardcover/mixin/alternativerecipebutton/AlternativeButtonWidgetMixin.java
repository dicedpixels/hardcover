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
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget.AlternativeButtonWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget.AlternativeButtonWidget.InputSlot;
import net.minecraft.client.gui.tooltip.HoveredTooltipPositioner;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.NetworkRecipeId;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.contract.RecipeResultsProvider;
import xyz.dicedpixels.hardcover.gui.RecipeTooltipComponent;
import xyz.dicedpixels.hardcover.gui.Textures;
import xyz.dicedpixels.hardcover.mixin.accessors.DrawContextInvoker;
import xyz.dicedpixels.hardcover.mixin.accessors.RecipeAlternativesWidgetAccessor;

@Mixin(AlternativeButtonWidget.class)
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
    private void hardcover$renderAlternativeButton(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
        if (Configs.alternativeRecipeButton.getValue()) {
            var client = MinecraftClient.getInstance();

            if (client != null) {
                if (isHovered()) {
                    var currentIndex = ((RecipeAlternativesWidgetAccessor) parent).hardcover$getCurrentIndexProvider().currentIndex();
                    ((DrawContextInvoker) context).hardcover$invokeDrawTooltip(client.textRenderer, RecipeTooltipComponent.asList(inputSlots, currentIndex), mouseX, mouseY, HoveredTooltipPositioner.INSTANCE, null);
                }

                context.drawGuiTexture(RenderLayer::getGuiTextured, hardcover$getWidgetTexture(), getX(), getY(), width, height);
                context.getMatrices().push();
                context.getMatrices().translate(0, 0, -35);
                context.drawItem(resultStack, getX() + 4, getY() + 4);
                context.getMatrices().pop();

                callbackInfo.cancel();
            }
        }
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void hardcover$setResultStack(RecipeAlternativesWidget recipeAlternativesWidget, int x, int y, NetworkRecipeId recipeId, boolean craftable, List<InputSlot> inputSlots, CallbackInfo callbackInfo) {
        resultStack = ((RecipeResultsProvider) parent).hardcover$getRecipeResults().get(recipeId);
    }
}
