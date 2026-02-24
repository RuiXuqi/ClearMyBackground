package com.clear.clearmybackground;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = Tags.MOD_ID,
        name = Tags.MOD_NAME,
        version = Tags.VERSION,
        clientSideOnly = true,
        acceptableRemoteVersions = "*",
        dependencies = "required-after:mixinbooter@[8.0,)",
        customProperties = {
                @Mod.CustomProperty(k = "license", v = "MIT"),
                @Mod.CustomProperty(k = "issueTrackerUrl", v = "https://github.com/RuiXuqi/ClearMyBackground/issues"),
                @Mod.CustomProperty(k = "iconItem", v = "minecraft:dirt")
        }
)
public class ClearMyBackground {
    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean GAME_LOADING_DONE = false;
    public static boolean FLUX_LOADING_LOADED = false;

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        GAME_LOADING_DONE = true;
        FLUX_LOADING_LOADED = Loader.isModLoaded("fluxloading");
    }
}
