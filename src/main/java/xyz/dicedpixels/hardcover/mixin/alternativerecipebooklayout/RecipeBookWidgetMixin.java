package xyz.dicedpixels.hardcover.mixin.alternativerecipebooklayout;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.util.Identifier;

import xyz.dicedpixels.hardcover.config.Configs;

@Mixin(RecipeBookWidget.class)
abstract class RecipeBookWidgetMixin {
    @Shadow
    @Final
    protected static Identifier TEXTURE;

    @Shadow
    protected abstract int getLeft();

    @Shadow
    protected abstract int getTop();

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;render(Lnet/minecraft/client/gui/DrawContext;IIF)V"))
    private void hardcover$hideRecipeBookTextureMagnifyingGlassPixel(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo callbackInfo) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, getLeft() + 10, getTop() + 24, 10.0F, 10.0F, 1, 1, 256, 256);
        }
    }

    @ModifyArgs(method = "reset", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;<init>(Lnet/minecraft/client/font/TextRenderer;IIIILnet/minecraft/text/Text;)V"))
    private void hardcover$modifySearchFieldDimensions(Args args, @Share("left") LocalIntRef refLeft, @Share("top") LocalIntRef refTop) {
        var top = getTop();

        if (Configs.alternativeRecipeBookLayout.getValue()) {
            var left = getLeft();

            refLeft.set(left);
            refTop.set(top);

            args.set(1, left + 11); // args[1] = x
            args.set(2, top + 12);  // args[2] = y
            args.set(3, 89);        // args[3] = width
            args.set(4, 16);        // args[4] = height
        } else {
            args.set(2, top + 12);  // args[2] = y
            args.set(4, 16);        // args[4] = height
        }
    }

    @ModifyArgs(method = "reset", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ScreenRect;of(Lnet/minecraft/client/gui/navigation/NavigationAxis;IIII)Lnet/minecraft/client/gui/ScreenRect;"))
    private void hardcover$modifySearchFieldScreenRectDimensions(Args args, @Share("left") LocalIntRef refLeft) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            args.set(1, refLeft.get() + 11); // args[1] = otherAxisCoord
        }
    }

    @ModifyArgs(method = "reset", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ToggleButtonWidget;<init>(IIIIZ)V"))
    private void hardcover$modifyToggleCraftableButtonDimensions(Args args, @Share("left") LocalIntRef refLeft) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            args.set(0, refLeft.get() + 102); // args[0] = x
            args.set(2, 16);                  // args[2] = width
        }
    }
}
