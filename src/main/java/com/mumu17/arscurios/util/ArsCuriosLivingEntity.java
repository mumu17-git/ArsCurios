package com.mumu17.arscurios.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ArsCuriosLivingEntity {
    private static final String EXTENDED_HAND_NAME = "ExtendedHand";

    private static void saveExtendedHandToTag(CompoundTag tag, @NotNull String hand) {
        tag.putString(EXTENDED_HAND_NAME, hand);
    }

    private static String loadExtendedHandFromTag(CompoundTag tag) {
        if (tag == null || !tag.contains(EXTENDED_HAND_NAME)) {
            return null;
        }
        return tag.getString(EXTENDED_HAND_NAME);
    }

    public static void setPlayerExtendedHand(LivingEntity entity, ExtendedHand hand) {
        CompoundTag tag = entity.getPersistentData();
        if (hand == null) {
            saveExtendedHandToTag(tag, ExtendedHand.MAIN_HAND.getSlotName());
            return;
        }
        saveExtendedHandToTag(tag, hand.getSlotName());
    }

    public static ExtendedHand getPlayerExtendedHand(LivingEntity entity) {
        CompoundTag tag = entity.getPersistentData();
        String handName = loadExtendedHandFromTag(tag);
        if (handName == null || handName.isEmpty()) {
            return ExtendedHand.MAIN_HAND;
        }
        return ExtendedHand.getSlotByName(handName);
    }
}
