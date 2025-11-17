package com.mumu17.arscurios.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(Mob.class)
public class MobMixin {

    @Shadow
    @Final
    private NonNullList<ItemStack> handItems;

    @ModifyArg(method = "getItemBySlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;get(I)Ljava/lang/Object;", ordinal = 0), index = 0)
    public int getItemBySlot(int original, @Local(argsOnly = true) EquipmentSlot slot) {
        return getIndexForMob(original, slot);
    }

    @ModifyArg(method = "setItemSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;set(ILjava/lang/Object;)Ljava/lang/Object;", ordinal = 0), index = 0)
    public int setItemSlot(int original, @Local(argsOnly = true) EquipmentSlot slot) {
        return getIndexForMob(original, slot);
    }

    @Inject(method = "setGuaranteedDrop", at = @At("HEAD"), cancellable = true)
    public void setGuaranteedDrop(EquipmentSlot slot, CallbackInfo ci) {
        if (isCuriosSlot(slot)) {
            ci.cancel();
        }
    }

    @Redirect(method = "getEquipmentDropChance", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EquipmentSlot;getIndex()I", ordinal = 0))
    public int getEquipmentDropChance(EquipmentSlot slot) {
        return getIndexForMob(slot.getIndex(), slot);
    }

    @Unique
    private boolean isCuriosSlot(EquipmentSlot slot) {
        return (slot.getType() == EquipmentSlot.Type.HAND && slot.getIndex() > handItems.size() - 1);
    }

    @Unique
    private int getIndexForMob(int original, EquipmentSlot slot) {
        return (isCuriosSlot(slot) ? 0 : original);
    }


}
