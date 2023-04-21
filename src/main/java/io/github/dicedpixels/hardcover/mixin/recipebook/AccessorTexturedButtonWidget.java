package io.github.dicedpixels.hardcover.mixin.recipebook;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.util.Identifier;

@Mixin(TexturedButtonWidget.class)
interface AccessorTexturedButtonWidget {
	@Accessor
	Identifier getTexture();
}
