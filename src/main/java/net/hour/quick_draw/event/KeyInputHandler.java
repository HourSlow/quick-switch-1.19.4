package net.hour.quick_draw.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    // Variables to track the last manually selected slot and previous tick's slot.
    private static int lastSlot = -1;
    private static int prevSlot = -1;

    // Create the key binding for the R key
    private static final KeyBinding lastHotbarKey = new KeyBinding(
            "key.switch.last", // Translation key for the key binding
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,             // R key
            "category.switch"  // Translation key for the category
    );

    public static void register() {
        // Register the key binding
        KeyBindingHelper.registerKeyBinding(lastHotbarKey);

        // Listen for tick events
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                int currentSlot = client.player.getInventory().selectedSlot;
                // Initialize prevSlot on first tick
                if (prevSlot == -1) {
                    prevSlot = currentSlot;
                }

                if (lastHotbarKey.wasPressed()) {
                    System.out.println("Swap Hand key pressed");
                    // Swap only if we have a valid lastSlot that's different from current
                    if (lastSlot != -1 && lastSlot != currentSlot) {
                        int temp = currentSlot;
                        client.player.getInventory().selectedSlot = lastSlot;
                        lastSlot = temp;
                    }
                } else {
                    // If the player manually changes the slot, update lastSlot to previous slot
                    if (currentSlot != prevSlot) {
                        lastSlot = prevSlot;
                        prevSlot = currentSlot;
                    }
                }
            }
        });
    }
}
