package net.hour.quick_draw.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    private static int lastSlot = -1;
    private static int prevSlot = -1;

    private static final KeyBinding lastHotbarKey = new KeyBinding(
            "key.switch.last",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "category.inventory"
    );

    public static void register() {
        KeyBindingHelper.registerKeyBinding(lastHotbarKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                int currentSlot = client.player.getInventory().selectedSlot;

                if (prevSlot == -1) {
                    prevSlot = currentSlot;
                }

                while (lastHotbarKey.wasPressed()) {
                    if (lastSlot != -1 && lastSlot != currentSlot) {
                        int temp = currentSlot;
                        client.player.getInventory().selectedSlot = lastSlot;
                        lastSlot = temp;
                    }
                }

                if (currentSlot != prevSlot) {
                    lastSlot = prevSlot;
                    prevSlot = currentSlot;
                }
            }
        });
    }
}
