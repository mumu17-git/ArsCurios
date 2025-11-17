package com.mumu17.arscurios.mixin;

import com.mumu17.arscurios.util.InteractionHandUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(EquipmentSlot.class)
@Unique
public class EquipmentSlotMixin {

    @Shadow
    @Final
    @Mutable
    private static EquipmentSlot[] $VALUES;

    @Invoker("<init>")
    public static EquipmentSlot invokeConstructor(String name, int ordinal, EquipmentSlot.Type type, int index, int filterFlag, String id) {
        throw new AssertionError();
    }

    static {
        List<EquipmentSlot> list = new ArrayList<>(Arrays.asList($VALUES));
        int i = 0;
        for (String handName : InteractionHandUtil.SLOT_ID.keySet()) {
            EquipmentSlot newHand = invokeConstructor(handName, list.size(), EquipmentSlot.Type.HAND, i + 2, i + 6, InteractionHandUtil.SLOT_ID.get(handName));
            list.add(newHand);
            i++;
        }
        $VALUES = list.toArray(new EquipmentSlot[0]);
    }
}
