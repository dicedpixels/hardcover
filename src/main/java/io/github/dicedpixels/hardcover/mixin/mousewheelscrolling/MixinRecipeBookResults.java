package io.github.dicedpixels.hardcover.mixin.mousewheelscrolling;

import io.github.dicedpixels.hardcover.Hardcover;
import io.github.dicedpixels.hardcover.interfaces.HardcoverMouseScrolled;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;

@Mixin(RecipeBookResults.class)
abstract class MixinRecipeBookResults implements HardcoverMouseScrolled {
	@Shadow
	private int pageCount;
	@Shadow
	private int currentPage;
	@Final
	@Shadow
	private RecipeAlternativesWidget alternatesWidget;

	@Override
	public void mouseScrolled(double mouseX, double mouseY, double amount) {
		if (alternatesWidget.isVisible()) {
			alternatesWidget.setVisible(false);
		}
		if (pageCount != 0) {
			if (amount == 1.0) {
				if (Hardcover.CONFIG.circularScrolling) {
					currentPage = (currentPage == pageCount - 1) ? 0 : currentPage + 1;
				} else {
					if (currentPage != pageCount - 1) {

						currentPage = currentPage + 1;
					}
				}
			} else if (amount == -1.0) {
				if (Hardcover.CONFIG.circularScrolling) {
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

	@Shadow
	protected abstract void refreshResultButtons();
}
