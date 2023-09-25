package xyz.dicedpixels.hardcover.client.mixin.mousewheelscrolling;

import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.dicedpixels.hardcover.Hardcover;
import xyz.dicedpixels.hardcover.client.contract.IMouseScrolled;

@Mixin(RecipeBookResults.class)
abstract class RecipeBookResultsMixin implements IMouseScrolled {
    @Shadow
    private int pageCount;

    @Shadow
    private int currentPage;

    @Final
    @Shadow
    private RecipeAlternativesWidget alternatesWidget;

    @Shadow
    protected abstract void refreshResultButtons();

    @Override
    public void hardcover$mouseScrolled(double verticalAmount) {
        if (alternatesWidget.isVisible()) {
            alternatesWidget.setVisible(false);
        }
        if (pageCount != 0) {
            if (verticalAmount == 1.0) {
                if (Hardcover.config().circularScrolling) {
                    currentPage = (currentPage == pageCount - 1) ? 0 : currentPage + 1;
                } else {
                    if (currentPage != pageCount - 1) {
                        currentPage = currentPage + 1;
                    }
                }
            } else if (verticalAmount == -1.0) {
                if (Hardcover.config().circularScrolling) {
                    currentPage = (currentPage == 0) ? pageCount - 1 : currentPage - 1;
                } else {
                    if (currentPage >= 1) {
                        currentPage = currentPage - 1;
                    }
                }
            }
            refreshResultButtons();
        }
    }
}
