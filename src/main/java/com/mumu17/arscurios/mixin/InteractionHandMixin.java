package com.mumu17.arscurios.mixin;

import com.mumu17.arscurios.util.InteractionHandUtil;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(InteractionHand.class)
@Unique
public class InteractionHandMixin {

    @Shadow
    @Final
    @Mutable
    private static InteractionHand[] $VALUES;

    @Invoker("<init>")
    public static InteractionHand invokeConstructor(String name, int ordinal) {
        throw new AssertionError();
    }

    static {
        List<InteractionHand> list = new ArrayList<>(Arrays.asList($VALUES));
        for (String handName : InteractionHandUtil.SLOT_ID.keySet()) {
            InteractionHand newHand = invokeConstructor(handName, list.size());
            list.add(newHand);
        }
        $VALUES = list.toArray(new InteractionHand[0]);

    }


}

