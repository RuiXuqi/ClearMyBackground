package com.clear.clearmybackground.mixin;

import com.clear.clearmybackground.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiScrollingList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nonnull;

@Mixin(value = GuiScrollingList.class, remap = false)
public abstract class GuiScrollingListMixin {
    @Shadow
    @Final
    protected int left;

    @Shadow
    @Final
    protected int top;

    @Shadow
    @Final
    protected int right;

    @Shadow
    @Final
    protected int bottom;

    @Shadow
    @Final
    private Minecraft client;

    @Shadow
    private float scrollDistance;

    @Redirect(
            method = "drawScreen(IIF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V",
                    ordinal = 0
            )
    )
    private void cancelBind(TextureManager instance, ResourceLocation resource) {
    }

    @Redirect(
            method = "drawScreen(IIF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/Tessellator;draw()V",
                    ordinal = 0
            )
    )
    private void redirectDraw(@Nonnull Tessellator instance) {
        instance.getBuffer().finishDrawing();
        GlStateManager.enableBlend();
        ClientHelper.renderListBackground(this.client, this.left, this.top, this.right, this.bottom, this.scrollDistance);
    }

    @Redirect(
            method = "drawScreen(IIF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/client/GuiScrollingList;drawGradientRect(IIIIII)V",
                    ordinal = 0
            )
    )
    private void redirectWorldDraw(GuiScrollingList instance, int left, int top, int right, int bottom, int color1, int color2) {
        GlStateManager.enableBlend();
        ClientHelper.renderListBackground(this.client, this.left, this.top, this.right, this.bottom, this.scrollDistance);
    }
}
