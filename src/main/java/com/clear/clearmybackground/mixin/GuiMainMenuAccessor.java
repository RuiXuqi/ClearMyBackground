package com.clear.clearmybackground.mixin;

import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GuiMainMenu.class)
public interface GuiMainMenuAccessor {
    @Invoker("renderSkybox")
    void invokeRenderSkybox(int mouseX, int mouseY, float partialTicks);

    @Accessor("panoramaTimer")
    float getPanoramaTimer();
}
