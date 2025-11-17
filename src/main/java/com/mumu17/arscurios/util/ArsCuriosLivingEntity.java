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

    protected ArsCuriosLivingEntity(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Override
    public @NotNull Iterable<ItemStack> getArmorSlots() {
        return ((LivingEntity) this).getArmorSlots();
    }

    @Override
    public @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot p_21127_) {
        return ((LivingEntity) this).getItemBySlot(p_21127_);
    }

    @Override
    public void setItemSlot(@NotNull EquipmentSlot p_21036_, @NotNull ItemStack p_21037_) {
        ((LivingEntity) this).setItemSlot(p_21036_, p_21037_);
    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return ((LivingEntity) this).getMainArm();
    }
}
