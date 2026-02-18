package com.clear.clearmybackground;

import com.clear.clearmybackground.mixin.early.GuiMainMenuAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

public class ClientHelper {

    /**
     * Creates a scissor test using minecraft screen coordinates instead of pixel coordinates.
     */
    public static void scissor(int screenX, int screenY, int boxWidth, int boxHeight) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int scale = scaledRes.getScaleFactor();

        int x = screenX * scale;
        int y = mc.displayHeight - (screenY * scale + boxHeight * scale);
        int width = Math.max(0, boxWidth * scale);
        int height = Math.max(0, boxHeight * scale);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(x, y, width, height);
    }

    /**
     * The {@link GuiMainMenu} instance used for panorama rendering.
     */
    public static @Nonnull GuiMainMenu MENU_INSTANCE = new GuiMainMenu();

    private static final ResourceLocation MENU_BACKGROUND = new ResourceLocation(ClearMyBackground.MOD_ID, "textures/gui/menu_background.png");
    private static final ResourceLocation MENU_LIST_BACKGROUND = new ResourceLocation(ClearMyBackground.MOD_ID, "textures/gui/menu_list_background.png");
    private static final ResourceLocation HEADER_SEPARATOR = new ResourceLocation(ClearMyBackground.MOD_ID, "textures/gui/header_separator.png");
    private static final ResourceLocation FOOTER_SEPARATOR = new ResourceLocation(ClearMyBackground.MOD_ID, "textures/gui/footer_separator.png");
    private static final ResourceLocation INWORLD_MENU_BACKGROUND = new ResourceLocation(ClearMyBackground.MOD_ID, "textures/gui/inworld_menu_background.png");
    private static final ResourceLocation INWORLD_MENU_LIST_BACKGROUND = new ResourceLocation(ClearMyBackground.MOD_ID, "textures/gui/inworld_menu_list_background.png");
    private static final ResourceLocation INWORLD_HEADER_SEPARATOR = new ResourceLocation(ClearMyBackground.MOD_ID, "textures/gui/inworld_header_separator.png");
    private static final ResourceLocation INWORLD_FOOTER_SEPARATOR = new ResourceLocation(ClearMyBackground.MOD_ID, "textures/gui/inworld_footer_separator.png");

    public static void renderWorldBackground(@Nonnull Minecraft mc, int width, int height) {
        if (mc.theWorld == null) {
            renderPanorama(mc);
        }
        renderMenuBackground(mc, width, height);
    }

    private static void renderMenuBackground(@Nonnull Minecraft mc, int width, int height) {
        boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        mc.getTextureManager().bindTexture(mc.theWorld == null ? MENU_BACKGROUND : INWORLD_MENU_BACKGROUND);
        Gui.func_146110_a(0, 0, 0.0F, 0.0F, width, height, 32, 32);

        if (blend) GL11.glEnable(GL11.GL_BLEND);
        else GL11.glDisable(GL11.GL_BLEND);
    }

    public static void renderPanorama(@Nonnull Minecraft mc) {
        final GuiMainMenu menu = MENU_INSTANCE;

        int oldWidth = menu.width;
        int oldHeight = menu.height;
        final ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        menu.setWorldAndResolution(mc, sr.getScaledWidth(), sr.getScaledHeight());

        boolean alpha = GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
        boolean depth = GL11.glIsEnabled(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        ((GuiMainMenuAccessor) menu).invokeRenderSkybox(0, 0, 0);

        if (alpha) GL11.glEnable(GL11.GL_ALPHA_TEST);
        else GL11.glDisable(GL11.GL_ALPHA_TEST);
        if (depth) GL11.glEnable(GL11.GL_DEPTH_TEST);
        else GL11.glDisable(GL11.GL_DEPTH_TEST);

        menu.width = oldWidth;
        menu.height = oldHeight;
    }

    public static void renderListSeparators(@Nonnull Minecraft mc, int left, int top, int right, int bottom) {
        boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        mc.getTextureManager().bindTexture(mc.theWorld == null ? HEADER_SEPARATOR : INWORLD_HEADER_SEPARATOR);
        Gui.func_146110_a(left, top - 2, 0.0F, 0.0F, right - left, 2, 32, 2);
        mc.getTextureManager().bindTexture(mc.theWorld == null ? FOOTER_SEPARATOR : INWORLD_FOOTER_SEPARATOR);
        Gui.func_146110_a(left, bottom, 0.0F, 0.0F, right - left, 2, 32, 2);

        if (blend) GL11.glEnable(GL11.GL_BLEND);
        else GL11.glDisable(GL11.GL_BLEND);
    }

    public static void renderListBackground(@Nonnull Minecraft mc, int left, int top, int right, int bottom, float amountScrolled) {
        boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        mc.getTextureManager().bindTexture(mc.theWorld == null ? MENU_LIST_BACKGROUND : INWORLD_MENU_LIST_BACKGROUND);
        Gui.func_146110_a(left, top, right, bottom + amountScrolled, right - left, bottom - top, 32, 32);

        if (blend) GL11.glEnable(GL11.GL_BLEND);
        else GL11.glDisable(GL11.GL_BLEND);
    }
}
