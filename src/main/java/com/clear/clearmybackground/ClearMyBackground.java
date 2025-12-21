package com.clear.clearmybackground;

import net.minecraftforge.fml.common.Mod;

@Mod(
        modid = Tags.MOD_ID,
        name = Tags.MOD_NAME,
        version = Tags.VERSION,
        clientSideOnly = true,
        acceptableRemoteVersions = "*",
        dependencies = "required-after:mixinbooter@[8.0,)"
)
public class ClearMyBackground {
}
