package com.clear.clearmybackground.mixin;

import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class LateMixin implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        final List<String> mixins = new ArrayList<>();
        if (Loader.isModLoaded("controlling")) {
            mixins.add("mixins.clearmybackground.controlling.json");
        }
        return mixins;
    }
}
