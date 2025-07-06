package xyz.dicedpixels.hardcover.mixin.creativetabs;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget.Tab;
import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.recipe.book.RecipeBookGroup;
import net.minecraft.screen.AbstractCraftingScreenHandler;
import net.minecraft.screen.AbstractRecipeScreenHandler;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.contract.TooltipProvider;
import xyz.dicedpixels.hardcover.feature.CreativeTabs;
import xyz.dicedpixels.hardcover.feature.CreativeTabs.CreativeTabsCategory;
import xyz.dicedpixels.hardcover.gui.Textures;
import xyz.dicedpixels.hardcover.gui.Textures.SelectableTexture;

@Mixin(RecipeBookWidget.class)
abstract class RecipeBookWidgetMixin<T extends AbstractRecipeScreenHandler> {
    @Shadow
    private @Nullable RecipeGroupButtonWidget currentTab;
    @Unique
    private int hardcover$currentTabPartition = 0;
    @Unique
    private ToggleButtonWidget hardcover$downButton;
    @Unique
    private List<List<RecipeGroupButtonWidget>> hardcover$tabPartitions = new ArrayList<>();
    @Unique
    private ToggleButtonWidget hardcover$upButton;
    @Shadow
    private int leftOffset;
    @Shadow
    private int parentHeight;
    @Shadow
    private int parentWidth;
    @Shadow
    private ClientRecipeBook recipeBook;
    @Shadow
    @Final
    private List<RecipeGroupButtonWidget> tabButtons;

    @Shadow
    protected abstract int getLeft();

    @Unique
    private ToggleButtonWidget hardcover$createButton(int x, int y, int width, int height, boolean toggled, SelectableTexture texture) {
        var button = new ToggleButtonWidget(x, y, width, height, toggled);

        button.setTextures(texture.asButtonTextures());
        return button;
    }

    @WrapOperation(method = { "populateAllRecipes", "refreshResults" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/recipebook/ClientRecipeBook;getResultsForCategory(Lnet/minecraft/recipe/book/RecipeBookGroup;)Ljava/util/List;"))
    private List<RecipeResultCollection> hardcover$getCreativeTabsResultCollection(ClientRecipeBook instance, RecipeBookGroup category, Operation<List<RecipeResultCollection>> original) {
        if (Configs.creativeTabs.getValue()) {
            if (CreativeTabs.isCraftingScreen()) {
                if (category instanceof CreativeTabsCategory creativeTabsCategory) {
                    return CreativeTabs.getResultsForCategory(creativeTabsCategory);
                }
            }
        }

        return original.call(instance, category);
    }

    @Unique
    private int hardcover$getDownButtonY(int y) {
        var tabHeight = 0;
        var yOffset = 0;

        if (Configs.compactCreativeTabs.getValue()) {
            tabHeight = 22;
            yOffset = 5;
        } else {
            tabHeight = 27;
            yOffset = 1;
        }

        return y + (tabHeight * hardcover$tabPartitions.get(hardcover$currentTabPartition).size()) + yOffset;
    }

    @Inject(method = "mouseClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ToggleButtonWidget;mouseClicked(DDI)Z"), cancellable = true)
    private void hardcover$onUpDownButtonsClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (CreativeTabs.isCraftingScreen()) {
            if (Configs.creativeTabs.getValue()) {
                if (hardcover$downButton.mouseClicked(mouseX, mouseY, button)) {
                    if (hardcover$currentTabPartition > 0) {
                        hardcover$currentTabPartition--;
                        hardcover$resetCurrentTab();
                        refreshTabButtons(isFilteringCraftable());
                        callbackInfoReturnable.setReturnValue(true);
                    }
                }

                if (hardcover$upButton.mouseClicked(mouseX, mouseY, button)) {
                    if (hardcover$currentTabPartition < hardcover$tabPartitions.size() - 1) {
                        hardcover$currentTabPartition++;
                        hardcover$resetCurrentTab();
                        refreshTabButtons(isFilteringCraftable());
                        callbackInfoReturnable.setReturnValue(true);
                    }
                }
            }
        }
    }

    @Inject(method = "refreshTabButtons", at = @At("HEAD"), cancellable = true)
    private void hardcover$refreshTabButtons(boolean filteringCraftable, CallbackInfo callbackInfo) {
        if (CreativeTabs.isCraftingScreen()) {
            if (Configs.creativeTabs.getValue()) {
                var initialX = (parentWidth - 147) / 2 - leftOffset - 30;

                if (Configs.centeredInventory.getValue()) {
                    initialX = initialX - (147 / 2) - 4;
                }

                var initialY = (parentHeight - 166) / 2 + 3;

                hardcover$setTabPartitions();
                hardcover$setUpUpDownButtons(initialX, initialY);
                hardcover$setUpTabButtons(filteringCraftable, initialX, initialY);

                callbackInfo.cancel();
            }
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ToggleButtonWidget;render(Lnet/minecraft/client/gui/DrawContext;IIF)V"))
    private void hardcover$renderUpDownButtons(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo callbackInfo) {
        if (CreativeTabs.isCraftingScreen()) {
            if (Configs.creativeTabs.getValue()) {
                if (hardcover$downButton != null && hardcover$upButton != null) {
                    hardcover$downButton.render(context, mouseX, mouseY, deltaTicks);
                    hardcover$upButton.render(context, mouseX, mouseY, deltaTicks);
                }
            }
        }
    }

    @Unique
    private void hardcover$resetCurrentTab() {
        for (var tab : tabButtons) {
            tab.setToggled(false);
        }

        currentTab = hardcover$tabPartitions.get(hardcover$currentTabPartition).getFirst();
        currentTab.setToggled(true);
        refreshResults(true, isFilteringCraftable());
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void hardcover$setCraftingScreen(AbstractRecipeScreenHandler craftingScreenHandler, List<Tab> tabs, CallbackInfo callbackInfo) {
        CreativeTabs.setCraftingScreen(craftingScreenHandler instanceof AbstractCraftingScreenHandler);
    }

    @Unique
    private void hardcover$setTabPartitions() {
        for (var widget : tabButtons) {
            widget.visible = false;
        }

        hardcover$tabPartitions = Lists.partition(tabButtons, Configs.compactCreativeTabs.getValue() ? 7 : 5);
    }

    @Unique
    private void hardcover$setUpTabButtons(boolean filteringCraftable, int x, int y) {
        var tabIndex = 0;

        for (var tabButton : hardcover$tabPartitions.get(hardcover$currentTabPartition)) {
            var category = tabButton.getCategory();

            if (category instanceof CreativeTabsCategory creativeTabsCategory) {
                tabButton.visible = true;

                if (Configs.compactCreativeTabs.getValue()) {
                    tabButton.setPosition(x + 8, y + 23 * tabIndex++);
                } else {
                    tabButton.setPosition(x, y + 27 * tabIndex++);
                }

                ((TooltipProvider) tabButton).hardcover$setTooltip(creativeTabsCategory.getName());
                tabButton.checkForNewRecipes(recipeBook, filteringCraftable);
            }
        }
    }

    @Unique
    private void hardcover$setUpUpDownButtons(int x, int y) {
        if (Configs.compactCreativeTabs.getValue()) {
            hardcover$downButton = hardcover$createButton(x + 12, 0, 16, 7, true, Textures.TABS_DOWN_COMPACT); // 162 = 27 * 6
            hardcover$upButton = hardcover$createButton(x + 12, y - 9, 16, 7, false, Textures.TABS_UP_COMPACT);
        } else {
            hardcover$downButton = hardcover$createButton(x + 7, 0, 17, 12, true, Textures.TABS_DOWN); // 135 = 27 * 5
            hardcover$upButton = hardcover$createButton(x + 7, y - 14, 17, 12, false, Textures.TABS_UP);
        }

        hardcover$downButton.setY(hardcover$getDownButtonY(y));
        hardcover$downButton.visible = hardcover$currentTabPartition > 0;
        hardcover$upButton.visible = hardcover$currentTabPartition < hardcover$tabPartitions.size() - 1;
    }

    @Shadow
    protected abstract boolean isFilteringCraftable();

    @Shadow
    protected abstract void refreshResults(boolean resetCurrentPage, boolean filteringCraftable);

    @Shadow
    protected abstract void refreshTabButtons(boolean filteringCraftable);
}
