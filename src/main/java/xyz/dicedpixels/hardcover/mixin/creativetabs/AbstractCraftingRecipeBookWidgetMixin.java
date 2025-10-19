package xyz.dicedpixels.hardcover.mixin.creativetabs;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget.Tab;
import net.minecraft.client.gui.screen.recipebook.CraftingRecipeBookWidget;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.feature.CreativeTabs;

@Mixin(CraftingRecipeBookWidget.class)
abstract class AbstractCraftingRecipeBookWidgetMixin {
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookWidget;<init>(Lnet/minecraft/screen/AbstractRecipeScreenHandler;Ljava/util/List;)V"), index = 1)
    private static List<Tab> hardcover$initCreativeTabs(List<Tab> originalTabs) {
        if (Configs.creativeTabs.getValue()) {
            var tabs = new ArrayList<Tab>();

            for (var category : CreativeTabs.getItemGroupCategories()) {
                tabs.add(new Tab(category.getIconItem(), category));
            }

            return tabs;
        }

        return originalTabs;
    }
}
