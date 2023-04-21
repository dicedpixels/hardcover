package io.github.dicedpixels.hardcover.mixin.mousewheelscrolling;

import io.github.dicedpixels.hardcover.Hardcover;
import io.github.dicedpixels.hardcover.interfaces.HardcoverMouseScrolled;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;

@Mixin(RecipeBookResults.class)
abstract class MixinRecipeBookResults implements HardcoverMouseScrolled {
	@Shadow
	private int pageCount;
	@Shadow
	private int currentPage;

	@Override
	public void mouseScrolled(double mouseX, double mouseY, double amount) {
		if (this.pageCount != 0) {
			if (amount == 1.0) {
				if (Hardcover.CONFIG.circularScrolling) {
					this.currentPage = (this.currentPage == this.pageCount - 1) ? 0 : this.currentPage + 1;
				} else {
					if (this.currentPage != this.pageCount - 1) {

						this.currentPage = this.currentPage + 1;
					}
				}
			} else if (amount == -1.0) {
				if (Hardcover.CONFIG.circularScrolling) {
					this.currentPage = (this.currentPage == 0) ? this.pageCount - 1 : this.currentPage - 1;
				} else {
					if (this.currentPage >= 1) {
						this.currentPage = this.currentPage - 1;
					}
				}
			}
			this.refreshResultButtons();
		}
	}

	@Shadow
	protected abstract void refreshResultButtons();
}
