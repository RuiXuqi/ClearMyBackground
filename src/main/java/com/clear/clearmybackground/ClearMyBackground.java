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
        dependencies = "required-after:mixinbooter@[8.0,)"
)
public class ClearMyBackground {
    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean FluxLoadingLoaded = false;

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        FluxLoadingLoaded = Loader.isModLoaded("fluxloading");
    }
}
