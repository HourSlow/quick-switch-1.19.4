package net.hour.quick_draw;

import net.fabricmc.api.ClientModInitializer;
import net.hour.quick_draw.event.KeyInputHandler;

public class QuickSwitchClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
    }
}
