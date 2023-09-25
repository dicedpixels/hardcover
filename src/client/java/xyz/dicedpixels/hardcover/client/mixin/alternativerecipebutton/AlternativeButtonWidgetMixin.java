package xyz.dicedpixels.hardcover.client.mixin.alternativerecipebutton;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeGridAligner;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.dicedpixels.hardcover.Hardcover;
import xyz.dicedpixels.hardcover.client.util.Textures;

@Mixin(targets = "net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget$AlternativeButtonWidget")
abstract class AlternativeButtonWidgetMixin extends ClickableWidget implements RecipeGridAligner<Ingredient> {
    @Unique
    private static final MinecraftClient client = MinecraftClient.getInstance();

    @Shadow
    @Final
    private boolean craftable;

    @Shadow
    @Final
    RecipeEntry<?> recipe;

    private AlternativeButtonWidgetMixin(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void hardcover$setTooltip(
            RecipeAlternativesWidget recipeAlternativesWidget,
            int x,
            int y,
            RecipeEntry<?> recipe,
            boolean craftable,
            CallbackInfo ci) {}

    @Inject(method = "renderButton", at = @At("HEAD"), cancellable = true)
    private void hardcover$renderAlternativeButton(
            DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (Hardcover.config().alternativeRecipeButton && client != null && client.world != null) {
            var texture = craftable
                    ? (isSelected() ? Textures.WIDGET_SELECTED.get() : Textures.WIDGET.get())
                    : (isSelected() ? Textures.WIDGET_DISABLED_SELECTED.get() : Textures.WIDGET_DISABLED.get());
            context.drawGuiTexture(texture, getX(), getY(), width, height);
            context.getMatrices().push();
            context.getMatrices().translate(0, 0, -35);
            context.drawItem(recipe.value().getResult(client.world.getRegistryManager()), getX() + 4, getY() + 4);
            context.getMatrices().pop();
            ci.cancel();
        }
    }
}
