package xyz.dicedpixels.hardcover.mixin.alternativerecipebutton;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget.AlternativeButtonWidget.InputSlot;
import net.minecraft.client.gui.tooltip.HoveredTooltipPositioner;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeGridAligner;
import net.minecraft.text.Text;

import xyz.dicedpixels.hardcover.Config;
import xyz.dicedpixels.hardcover.Textures;
import xyz.dicedpixels.hardcover.gui.RecipeTooltipComponent;
import xyz.dicedpixels.hardcover.mixin.accessors.DrawContextAccessor;
import xyz.dicedpixels.hardcover.mixin.accessors.RecipeAlternativesWidgetAccessor;

@Mixin(RecipeAlternativesWidget.AlternativeButtonWidget.class)
abstract class AlternativeButtonWidgetMixin extends ClickableWidget implements RecipeGridAligner<Ingredient> {
    @Shadow
    @Final
    protected List<InputSlot> slots;

    @Shadow(aliases = "field_3113")
    RecipeAlternativesWidget parent;

    @Shadow
    @Final
    RecipeEntry<?> recipe;

    @Shadow
    @Final
    private boolean craftable;

    private AlternativeButtonWidgetMixin(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @SuppressWarnings("resource")
    @Inject(method = "renderWidget", at = @At("HEAD"), cancellable = true)
    private void hardcover$renderAlternativeButton(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
        var client = ((RecipeAlternativesWidgetAccessor) parent).hardcover$client();

        if (Config.instance().alternativeRecipeButton && client != null && client.world != null) {
            if (isHovered()) {
                ((DrawContextAccessor) context).hardcover$drawTooltip(client.textRenderer, List.of(new RecipeTooltipComponent(slots, ((RecipeAlternativesWidgetAccessor) parent).hardcover$time())), mouseX, mouseY, HoveredTooltipPositioner.INSTANCE);
            }

            var texture = craftable ? (isSelected() ? Textures.WIDGET_SELECTED.get() : Textures.WIDGET.get()) : (isSelected() ? Textures.WIDGET_DISABLED_SELECTED.get() : Textures.WIDGET_DISABLED.get());

            context.drawGuiTexture(texture, getX(), getY(), width, height);
            context.getMatrices().push();
            context.getMatrices().translate(0, 0, -35);
            context.drawItem(recipe.value().getResult(client.world.getRegistryManager()), getX() + 4, getY() + 4);
            context.getMatrices().pop();

            callbackInfo.cancel();
        }
    }
}
