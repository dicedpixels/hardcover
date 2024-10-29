package xyz.dicedpixels.hardcover.mixin.mousewheelscrolling;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;

import xyz.dicedpixels.hardcover.Config;
import xyz.dicedpixels.hardcover.IMouseScrolled;

@Mixin(RecipeBookResults.class)
abstract class RecipeBookResultsMixin implements IMouseScrolled {
    @Final
    @Shadow
    private RecipeAlternativesWidget alternatesWidget;

    @Shadow
    private int currentPage;

    @Unique
    private int hardcover$parentLeft = 0;
    @Unique

    private int hardcover$parentTop = 0;

    @Shadow
    private int pageCount;

    @Inject(method = "initialize", at = @At("HEAD"))
    private void hardcover$SetParentBounds(MinecraftClient client, int parentLeft, int parentTop, CallbackInfo ci) {
        hardcover$parentTop = parentTop;
        hardcover$parentLeft = parentLeft;
    }

    @Override
    public int hardcover$getParentLeft() {
        return hardcover$parentLeft;
    }

    @Override
    public int hardcover$getParentTop() {
        return hardcover$parentTop;
    }

    @Override
    public void hardcover$mouseScrolled(double verticalAmount) {
        if (alternatesWidget.isVisible()) {
            alternatesWidget.setVisible(false);
        }

        if (pageCount != 0) {
            if (verticalAmount == 1.0) {
                if (Config.instance().circularScrolling) {
                    currentPage = (currentPage == pageCount - 1) ? 0 : currentPage + 1;
                } else if (currentPage != pageCount - 1) {
                    currentPage++;
                }
            } else if (verticalAmount == -1.0) {
                if (Config.instance().circularScrolling) {
                    currentPage = (currentPage == 0) ? pageCount - 1 : currentPage - 1;
                } else if (currentPage >= 1) {
                    currentPage--;
                }
            }

            refreshResultButtons();
        }
    }

    @Shadow
    protected abstract void refreshResultButtons();
}
