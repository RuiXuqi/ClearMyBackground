package com.clear.clearmybackground.mixin;

import com.clear.clearmybackground.ClearMyBackground;
import com.clear.clearmybackground.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenWorking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;

@Mixin(GuiScreen.class)
public class GuiScreenMixin {
    @Shadow
    public Minecraft mc;

    @Shadow
    public int width;

    @Shadow
    public int height;

    @Inject(method = "drawWorldBackground", at = @At("HEAD"), cancellable = true)
    public void drawWorldBackground(int tint, @Nonnull CallbackInfo ci) {
        GuiScreen self = (GuiScreen) (Object) this;
        if (ClearMyBackground.FluxLoadingLoaded && (self instanceof GuiScreenWorking || self instanceof GuiDownloadTerrain)) return;
        ClientHelper.renderWorldBackground(this.mc,this.width, this.height);
        ci.cancel();
    }

    @Inject(method = "drawBackground", at = @At("HEAD"), cancellable = true)
    public void drawBackground(int tint, @Nonnull CallbackInfo ci) {
        GuiScreen self = (GuiScreen) (Object) this;
        if (ClearMyBackground.FluxLoadingLoaded && (self instanceof GuiScreenWorking || self instanceof GuiDownloadTerrain)) return;
        ClientHelper.renderWorldBackground(this.mc,this.width, this.height);
        ci.cancel();
    }
}
