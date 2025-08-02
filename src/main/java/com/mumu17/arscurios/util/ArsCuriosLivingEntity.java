package com.mumu17.arscurios.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArsCuriosLivingEntity extends LivingEntity {
    private static final String EXTENDED_HAND_NAME = "ExtendedHand";

    protected ArsCuriosLivingEntity(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

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
        if (entity == null) return;
        CompoundTag tag = entity.getPersistentData();
        if (hand == null) {
            saveExtendedHandToTag(tag, ExtendedHand.MAIN_HAND.getSlotName());
            return;
        }
        saveExtendedHandToTag(tag, hand.getSlotName());
    }

    public static ExtendedHand getPlayerExtendedHand(LivingEntity entity) {
        if (entity == null) return ExtendedHand.MAIN_HAND;
        CompoundTag tag = entity.getPersistentData();
        String handName = loadExtendedHandFromTag(tag);
        if (handName == null || handName.isEmpty()) {
            return ExtendedHand.MAIN_HAND;
        }
        return ExtendedHand.getSlotByName(handName);
    }

    @Override
    public @NotNull Iterable<ItemStack> getArmorSlots() {
        return ((LivingEntity) this).getArmorSlots();
    }

    @Override
    public @NotNull ItemStack getItemBySlot(@Nullable EquipmentSlot p_21127_) {
        if (p_21127_ == null) {
            return ArsCuriosInventoryHelper.getCuriosInventoryItem(this, getPlayerExtendedHand(this).getSlotName());
        }
        return ((LivingEntity) this).getItemBySlot(p_21127_);
    }

    @Override
    public void setItemSlot(@Nullable EquipmentSlot p_21036_, @NotNull ItemStack p_21037_) {
    if (p_21036_ == null) {
            ArsCuriosInventoryHelper.setCuriosInventoryItem(this, getPlayerExtendedHand(this).getSlotName(), p_21037_);
            return;
        }
        ((LivingEntity) this).setItemSlot(p_21036_, p_21037_);
    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return ((LivingEntity) this).getMainArm();
    }
}
