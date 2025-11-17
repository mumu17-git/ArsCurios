package com.mumu17.arscurios.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mumu17.arscurios.util.ArsCuriosInventoryHelper;
import com.mumu17.arscurios.util.InteractionHandUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Shadow
    @Final
    private NonNullList<ItemStack> lastHandItemStacks;

    @Unique
    private final NonNullList<ItemStack> lastExtendedHandItemStacks = NonNullList.withSize(InteractionHandUtil.SLOT_ID.size(), ItemStack.EMPTY);

    @Inject(method = "getItemInHand", at = @At(value = "HEAD"), cancellable = true)
    public void getItemInHand(InteractionHand hand, CallbackInfoReturnable<ItemStack> cir) {
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        if (InteractionHandUtil.isCurios(hand)) {
            ItemStack itemStack = ArsCuriosInventoryHelper.getCuriosInventoryItem(livingEntity, InteractionHandUtil.getSlotName(hand));
            cir.setReturnValue(itemStack);
            cir.cancel();
        }
    }

    @Inject(method = "setItemInHand", at = @At(value = "HEAD"), cancellable = true)
    public void setItemInHand(InteractionHand hand, ItemStack itemStack, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        if (InteractionHandUtil.isCurios(hand)) {
            if (ArsCuriosInventoryHelper.setCuriosInventoryItem(livingEntity, InteractionHandUtil.getSlotName(hand), itemStack)) {
                ci.cancel();
            }
        }
    }

    @ModifyArg(method = "getLastHandItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;get(I)Ljava/lang/Object;"), index = 0)
    public int ModArg$getLastHandItem(int original, @Local(argsOnly = true) EquipmentSlot slot) {
        int index = slot.getIndex() - lastHandItemStacks.size();
        return (slot.getType() == EquipmentSlot.Type.HAND && index >= 0 ? 0 : original);
    }

    @ModifyReturnValue(method = "getLastHandItem", at = @At("RETURN"))
    public ItemStack ModReturn$getLastHandItem(ItemStack original, @Local(argsOnly = true) EquipmentSlot slot) {
        int index = slot.getIndex() - lastHandItemStacks.size();
        if (slot.getType() == EquipmentSlot.Type.HAND && index >= 0 && index < this.lastExtendedHandItemStacks.size()) {
            return this.lastExtendedHandItemStacks.get(index);
        }
        return original;
    }

    @Inject(method = "setLastHandItem", at = @At("HEAD"), cancellable = true)
    public void ModReturn$setLastHandItem(EquipmentSlot slot, ItemStack itemStack, CallbackInfo ci) {
        int index = slot.getIndex() - lastHandItemStacks.size();
        if (slot.getType() == EquipmentSlot.Type.HAND && index >= 0 && index < this.lastExtendedHandItemStacks.size()) {
            this.lastExtendedHandItemStacks.set(index, itemStack);
            ci.cancel();
        }
    }
}
