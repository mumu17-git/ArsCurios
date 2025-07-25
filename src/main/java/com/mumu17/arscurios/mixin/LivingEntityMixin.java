package com.mumu17.arscurios.mixin;

import com.mumu17.arscurios.util.ArsCuriosInventoryHelper;
import com.mumu17.arscurios.util.ArsCuriosLivingEntity;
import com.mumu17.arscurios.util.ExtendedHand;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "getItemInHand", at = @At(value = "HEAD"), cancellable = true)
    public void getItemInHand(InteractionHand hand, CallbackInfoReturnable<ItemStack> cir) {
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        ExtendedHand extendedHand = (hand != null ? ExtendedHand.valueOf(hand.name()) : ArsCuriosLivingEntity.getPlayerExtendedHand(livingEntity));
        if (extendedHand.isCurios()) {
            ItemStack itemStack = ArsCuriosInventoryHelper.getCuriosInventoryItem(livingEntity, extendedHand.getSlotName());
            if (!itemStack.isEmpty()) {
                cir.setReturnValue(itemStack);
                cir.cancel();
            }
        }
    }

    @Inject(method = "setItemInHand", at = @At(value = "HEAD"), cancellable = true)
    public void setItemInHand(InteractionHand hand, ItemStack itemStack, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        ExtendedHand extendedHand = (hand != null ? ExtendedHand.valueOf(hand.name()) : ArsCuriosLivingEntity.getPlayerExtendedHand(livingEntity));
        if (extendedHand.isCurios()) {
            if (ArsCuriosInventoryHelper.setCuriosInventoryItem(livingEntity, extendedHand.getSlotName(), itemStack)) {
                ci.cancel();
            }
        }
    }
}
