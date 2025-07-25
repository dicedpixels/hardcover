package xyz.dicedpixels.hardcover.mixin.mousewheelscrolling;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.contract.MouseScrollable;
import xyz.dicedpixels.hardcover.contract.TabPartitionProvider;

@Mixin(RecipeBookWidget.class)
abstract class RecipeBookWidgetMixin implements MouseScrollable, TabPartitionProvider {
    @Shadow
    @Final
    private List<RecipeGroupButtonWidget> tabButtons;

    @Override
    public void hardcover$mouseScrolled(double verticalAmount) {
        if (!tabButtons.isEmpty()) {
            if (verticalAmount == 1.0) {
                if (Configs.invertScrollDirection.getValue()) {
                    hardcover$decrementTabPartition();
                } else {
                    hardcover$incrementTabPartition();
                }
            } else if (verticalAmount == -1.0) {
                if (Configs.invertScrollDirection.getValue()) {
                    hardcover$incrementTabPartition();
                } else {
                    hardcover$decrementTabPartition();
                }
            }
        }
    }
}
