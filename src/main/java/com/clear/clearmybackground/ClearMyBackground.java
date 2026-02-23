package com.clear.clearmybackground;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = ClearMyBackground.MOD_ID,
        name = ClearMyBackground.MOD_NAME,
        version = Tags.VERSION,
        acceptableRemoteVersions = "*",
        dependencies = "required-after:unimixins",
        customProperties = {
                @Mod.CustomProperty(k = "license", v = "MIT"),
                @Mod.CustomProperty(k = "issueTrackerUrl", v = "https://github.com/RuiXuqi/ClearMyBackground/issues"),
                @Mod.CustomProperty(k = "iconItem", v = "minecraft:dirt")
        }
)
public class ClearMyBackground {
    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean GAME_LOADING_DONE = false;

    public static final String MOD_ID = "clearmybackground";
    public static final String MOD_NAME = "Clear My Background";

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        GAME_LOADING_DONE = true;
    }
}
