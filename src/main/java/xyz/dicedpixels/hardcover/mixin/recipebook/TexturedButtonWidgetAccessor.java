package xyz.dicedpixels.hardcover.mixin.recipebook;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.widget.TexturedButtonWidget;

@Mixin(TexturedButtonWidget.class)
public interface TexturedButtonWidgetAccessor {
    @Accessor("textures")
    ButtonTextures hardcover$textures();
}
