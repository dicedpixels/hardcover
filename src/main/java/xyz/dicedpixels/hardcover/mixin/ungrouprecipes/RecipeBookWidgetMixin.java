package xyz.dicedpixels.hardcover.mixin.ungrouprecipes;

import java.util.Comparator;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.recipe.display.SlotDisplayContexts;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.context.ContextParameterMap;

import xyz.dicedpixels.hardcover.config.Configs;

@Mixin(RecipeBookWidget.class)
abstract class RecipeBookWidgetMixin {
    @Shadow
    protected MinecraftClient client;

    @Unique
    private static Identifier harddcover$getIdentifier(RecipeResultCollection collection, ContextParameterMap context) {
        return Registries.ITEM.getId(collection.getAllRecipes().getFirst().getStacks(context).getFirst().getItem());
    }

    @ModifyArg(method = "refreshResults", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookResults;setResults(Ljava/util/List;ZZ)V"), index = 0)
    private List<RecipeResultCollection> hardcover$sortRecipesById(List<RecipeResultCollection> resultCollections) {
        if (Configs.ungroupRecipes.getValue() && client.world != null) {
            var context = SlotDisplayContexts.createParameters(client.world);

            resultCollections.sort(Comparator.comparing(collection -> harddcover$getIdentifier(collection, context)));
        }

        return resultCollections;
    }
}
