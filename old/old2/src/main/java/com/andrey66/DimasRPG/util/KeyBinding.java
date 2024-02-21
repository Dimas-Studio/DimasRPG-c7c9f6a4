package com.andrey66.DimasRPG.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_DIMAS_RPG = "key.category.dimas_rpg.dimas_rpg";
    public static final String KEY_OPEN_CLASS_MENU = "key.dimas_rpg.open_class_menu";

    public static final KeyMapping OPEN_CLASS_MENU = new KeyMapping(KEY_OPEN_CLASS_MENU, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, KEY_CATEGORY_DIMAS_RPG);
}
