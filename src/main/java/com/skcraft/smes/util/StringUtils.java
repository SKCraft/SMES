package com.skcraft.smes.util;

import com.skcraft.smes.SMES;
import net.minecraft.util.StatCollector;

public class StringUtils {
    public static String translate(String unlocalized, boolean prefix) {
        return prefix ? StatCollector.translateToLocal(SMES.PREFIX + unlocalized) : StatCollector.translateToLocal(unlocalized);
    }
    public static String translate(String unlocalized) {
        return translate(unlocalized, true);
    }
}
