package xyz.dicedpixels.hardcover.mixin.mousewheelscrolling;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.contract.MouseScrollable;

@Mixin(RecipeBookResults.class)
abstract class RecipeBookResultsMixin implements MouseScrollable {
    @Final
    @Shadow
    private RecipeAlternativesWidget alternatesWidget;

    @Shadow
    private int currentPage;

    @Shadow
    private int pageCount;

    @Unique
    private void hardcover$decrementCurrentPage() {
        if (Configs.circularScrolling.getValue()) {
            currentPage = (currentPage == 0) ? pageCount - 1 : currentPage - 1;
        } else if (currentPage >= 1) {
            currentPage--;
        }
    }

    @Unique
    private void hardcover$incrementCurrentPage() {
        if (Configs.circularScrolling.getValue()) {
            currentPage = (currentPage == pageCount - 1) ? 0 : currentPage + 1;
        } else if (currentPage != pageCount - 1) {
            currentPage++;
        }
    }

    @Override
    public void hardcover$mouseScrolled(double verticalAmount) {
        if (alternatesWidget.isVisible()) {
            alternatesWidget.setVisible(false);
        }

        if (pageCount != 0) {
            if (verticalAmount == 1.0) {
                if (Configs.invertScrollDirection.getValue()) {
                    hardcover$decrementCurrentPage();
                } else {
                    hardcover$incrementCurrentPage();
                }
            } else if (verticalAmount == -1.0) {
                if (Configs.invertScrollDirection.getValue()) {
                    hardcover$incrementCurrentPage();
                } else {
                    hardcover$decrementCurrentPage();
                }
            }

            refreshResultButtons();
        }
    }

    @Shadow
    protected abstract void refreshResultButtons();
}
