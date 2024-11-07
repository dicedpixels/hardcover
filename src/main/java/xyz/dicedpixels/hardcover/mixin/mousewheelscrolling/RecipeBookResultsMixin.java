package xyz.dicedpixels.hardcover.mixin.mousewheelscrolling;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;

import xyz.dicedpixels.hardcover.Config;
import xyz.dicedpixels.hardcover.interfaces.IMouseScrolled;

@Mixin(RecipeBookResults.class)
abstract class RecipeBookResultsMixin implements IMouseScrolled {
    @Final
    @Shadow
    private RecipeAlternativesWidget alternatesWidget;

    @Shadow
    private int currentPage;

    @Shadow
    private int pageCount;

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
