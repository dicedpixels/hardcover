package xyz.dicedpixels.hardcover.feature;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.Type;
import net.minecraft.recipe.NetworkRecipeId;
import net.minecraft.screen.slot.SlotActionType;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import xyz.dicedpixels.hardcover.mixin.accessors.KeyBindingAccessor;

public final class QuickCraft {
    private static final int CRAFTING_DELAY = 2;
    private static final KeyBinding quickCraftKey = new KeyBinding("hardcover.key.quick_craft", Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "hardcover.key.category");
    public static NetworkRecipeId clickedRecipeId = null;
    public static int currentDelay = CRAFTING_DELAY;
    public static NetworkRecipeId requestedCraftRecipeId = null;
    public static boolean scheduled = false;

    public static void doQuickMove(Screen currentScreen, ClientPlayerEntity player, ClientPlayerInteractionManager interactionManager) {
        if (currentScreen instanceof HandledScreen<?> handledScreen) {
            if (clickedRecipeId != null && requestedCraftRecipeId != null) {
                if (clickedRecipeId == requestedCraftRecipeId) {
                    clickedRecipeId = null;
                    requestedCraftRecipeId = null;
                    interactionManager.clickSlot(handledScreen.getScreenHandler().syncId, 0, 0, SlotActionType.QUICK_MOVE, player);
                }
            }
        }
    }

    public static boolean hasKeyDown() {
        return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), ((KeyBindingAccessor) quickCraftKey).hardcover$getBoundKey().getCode());
    }

    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(QuickCraft::scheduleCraft);
        KeyBindingHelper.registerKeyBinding(quickCraftKey);
    }

    public static void scheduleCraft(MinecraftClient client) {
        if (scheduled) {
            if (currentDelay == 0) {
                doQuickMove(client.currentScreen, client.player, client.interactionManager);
                scheduled = false;
                currentDelay = CRAFTING_DELAY;
            } else {
                currentDelay--;
            }
        }
    }
}
