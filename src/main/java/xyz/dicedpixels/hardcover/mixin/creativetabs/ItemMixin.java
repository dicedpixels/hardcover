package xyz.dicedpixels.hardcover.mixin.creativetabs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import xyz.dicedpixels.hardcover.contract.ItemGroupProvider;

@Mixin(Item.class)
abstract class ItemMixin implements ItemGroupProvider {
    @Unique
    private ItemGroup hardcover$itemGroup;

    @Override
    public ItemGroup hardcover$getItemGroup() {
        return hardcover$itemGroup;
    }

    @Override
    public void hardcover$setItemGroup(ItemGroup itemGroup) {
        this.hardcover$itemGroup = itemGroup;
    }
}
