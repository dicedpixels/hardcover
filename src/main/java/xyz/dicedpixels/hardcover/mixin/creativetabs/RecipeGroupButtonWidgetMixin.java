package xyz.dicedpixels.hardcover.mixin.creativetabs;

import java.util.List;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.recipe.book.RecipeBookGroup;
import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import net.minecraft.util.Formatting;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.contract.TooltipProvider;
import xyz.dicedpixels.hardcover.feature.CreativeTabs;
import xyz.dicedpixels.hardcover.feature.CreativeTabs.CreativeTabsCategory;
import xyz.dicedpixels.hardcover.gui.Textures;

@Mixin(RecipeGroupButtonWidget.class)
abstract class RecipeGroupButtonWidgetMixin extends TexturedButtonWidget implements TooltipProvider {
    @Unique
    private MutableText hardcover$tooltip = MutableText.of(PlainTextContent.EMPTY);

    public RecipeGroupButtonWidgetMixin(int x, int y, int width, int height, ButtonTextures textures, PressAction pressAction) {
        super(x, y, width, height, textures, pressAction);
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TexturedButtonWidget;<init>(IIIILnet/minecraft/client/gui/screen/ButtonTextures;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;)V"), index = 4)
    private static ButtonTextures hardcover$setTabTexture(ButtonTextures original) {
        if (CreativeTabs.isCraftingScreen()) {
            if (Configs.creativeTabs.getValue() && Configs.compactCreativeTabs.getValue()) {
                return Textures.TAB_COMPACT.asButtonTextures();
            }
        }

        return original;
    }

    @WrapOperation(method = { "checkForNewRecipes", "hasKnownRecipes" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/recipebook/ClientRecipeBook;getResultsForCategory(Lnet/minecraft/recipe/book/RecipeBookGroup;)Ljava/util/List;"))
    private List<RecipeResultCollection> hardcover$getCreativeTabsResultCollection(ClientRecipeBook instance, RecipeBookGroup category, Operation<List<RecipeResultCollection>> original) {
        if (CreativeTabs.isCraftingScreen()) {
            if (Configs.creativeTabs.getValue()) {
                if (category instanceof CreativeTabsCategory creativeTabsCategory) {
                    return CreativeTabs.getResultsForCategory(creativeTabsCategory);
                }
            }
        }

        return original.call(instance, category);
    }

    @ModifyArgs(method = "renderIcons", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawItemWithoutEntity(Lnet/minecraft/item/ItemStack;II)V", ordinal = 2))
    private void hardcover$modifyItemIconPositions(Args args) {
        if (CreativeTabs.isCraftingScreen()) {
            if (Configs.creativeTabs.getValue() && Configs.compactCreativeTabs.getValue()) {
                args.set(1, getX() + 4); // x
                args.set(2, getY() + 3); // y
            }
        }
    }

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TexturedButtonWidget;<init>(IIIILnet/minecraft/client/gui/screen/ButtonTextures;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;)V"))
    private static void hardcover$setTextureSize(Args args) {
        if (CreativeTabs.isCraftingScreen()) {
            if (Configs.creativeTabs.getValue() && Configs.compactCreativeTabs.getValue()) {
                args.set(2, 27); // width
                args.set(3, 22); // height
            }
        }
    }

    @Inject(method = "drawIcon", at = @At("TAIL"))
    private void hardcover$renderTooltipState(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo callbackInfo) {
        if (Configs.creativeTabs.getValue()) {
            var adjusted = false;

            if (Configs.compactCreativeTabs.getValue()) {
                adjusted = getX() + width - 5 > mouseX;
            } else {
                adjusted = getX() + width - 4 > mouseX;
            }

            if (isHovered() && adjusted && !hardcover$tooltip.getString().isEmpty()) {
                context.drawTooltip(MinecraftClient.getInstance().textRenderer, hardcover$tooltip, mouseX, mouseY);
            }
        }
    }

    @Override
    public void hardcover$setTooltip(@NonNull MutableText text) {
        hardcover$tooltip = text.copy().styled(style -> style.withFormatting(Formatting.BLUE));
    }
}
