package xyz.dicedpixels.hardcover.feature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroup.Type;
import net.minecraft.item.ItemGroups;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.display.SlotDisplayContexts;
import net.minecraft.registry.Registries;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import xyz.dicedpixels.hardcover.contract.ItemGroupProvider;

public final class CreativeTabs {
    private static final Map<CreativeTabsCategory, List<RecipeResultCollection>> categoryRecipeResultMap = new HashMap<>();
    private static final Map<ItemGroup, CreativeTabsCategory> itemGroupCategoryMap = new HashMap<>();
    private static boolean craftingScreen;

    public static Collection<CreativeTabsCategory> getItemGroupCategories() {
        var list = new ArrayList<>(itemGroupCategoryMap.values());

        list.sort(Comparator.comparing(category -> category.getName().getString()));
        return list;
    }

    public static List<RecipeResultCollection> getResultsForCategory(CreativeTabsCategory category) {
        return categoryRecipeResultMap.getOrDefault(category, List.of());
    }

    public static void groupOrderedResults(World world, List<RecipeResultCollection> orderedResults) {
        categoryRecipeResultMap.clear();
        itemGroupCategoryMap.clear();
        ItemGroups.updateDisplayContext(FeatureFlags.FEATURE_MANAGER.getFeatureSet(), false, world.getRegistryManager());

        var context = SlotDisplayContexts.createParameters(world);

        for (var entry : Registries.ITEM_GROUP.getEntrySet()) {
            if (entry.getKey() == ItemGroups.OPERATOR) {
                continue;
            }

            var group = entry.getValue();

            if (group.getType() == Type.CATEGORY) {
                itemGroupCategoryMap.put(group, CreativeTabsCategory.of(group));

                for (var item : group.getSearchTabStacks()) {
                    ((ItemGroupProvider) item.getItem()).hardcover$setItemGroup(group);
                }
            }
        }

        for (var resultCollection : orderedResults) {
            var item = resultCollection.getAllRecipes().getFirst().getStacks(context).getFirst().getItem();
            var group = ((ItemGroupProvider) item).hardcover$getItemGroup();

            categoryRecipeResultMap.computeIfAbsent(itemGroupCategoryMap.get(group), key -> new ArrayList<>()).add(resultCollection);
        }
    }

    public static boolean isCraftingScreen() {
        return craftingScreen;
    }

    public static void setCraftingScreen(boolean craftingScreen) {
        CreativeTabs.craftingScreen = craftingScreen;
    }

    public static class CreativeTabsCategory extends RecipeBookCategory {
        private final ItemGroup itemGroup;

        private CreativeTabsCategory(ItemGroup itemGroup) {
            this.itemGroup = itemGroup;
        }

        public static CreativeTabsCategory of(ItemGroup itemGroup) {
            return new CreativeTabsCategory(itemGroup);
        }

        public Item getIconItem() {
            return itemGroup.getIcon().getItem();
        }

        public Text getName() {
            return itemGroup.getDisplayName();
        }
    }
}
