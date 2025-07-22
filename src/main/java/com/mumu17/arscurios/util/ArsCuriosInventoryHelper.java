package com.mumu17.arscurios.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ArsCuriosInventoryHelper {

    public static ItemStack getCuriosInventoryItem(LivingEntity livingEntity, String curiosSlot) {
        LazyOptional<ICuriosItemHandler> curiosItemHandlerLazyOptional = CuriosApi.getCuriosInventory(livingEntity);
        AtomicReference<ItemStack> itemStack = new AtomicReference<>(ItemStack.EMPTY);
        curiosItemHandlerLazyOptional.ifPresent(curiosItemHandler -> {
            Map<String, ICurioStacksHandler> curiosMap = curiosItemHandler.getCurios();
            if (curiosMap.containsKey(curiosSlot)) {
                ICurioStacksHandler stacksHandler = curiosMap.get(curiosSlot);
                for (int i = 0; i < stacksHandler.getSlots(); i++) {
                    if (!stacksHandler.getStacks().getStackInSlot(i).isEmpty()) {
                        itemStack.set(stacksHandler.getStacks().getStackInSlot(i));
                    }
                }
            }
        });
        return itemStack.get();
    }

    public static boolean setCuriosInventoryItem(LivingEntity livingEntity, String curiosSlot, ItemStack itemStack) {
        LazyOptional<ICuriosItemHandler> curiosItemHandlerLazyOptional = CuriosApi.getCuriosInventory(livingEntity);
        AtomicReference<Boolean> isSet = new AtomicReference<>(false);
        curiosItemHandlerLazyOptional.ifPresent(curiosItemHandler -> {
            Map<String, ICurioStacksHandler> curiosMap = curiosItemHandler.getCurios();
            if (curiosMap.containsKey(curiosSlot)) {
                ICurioStacksHandler stacksHandler = curiosMap.get(curiosSlot);
                for (int i = 0; i < stacksHandler.getSlots(); i++) {
                    if (stacksHandler.getStacks().getStackInSlot(i).isEmpty()) {
                        stacksHandler.getStacks().setStackInSlot(i, itemStack);
                        break;
                    }
                }
            }
        });
        return isSet.get();
    }
}