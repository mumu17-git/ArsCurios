package com.mumu17.arscurios.util;


import net.minecraft.world.InteractionHand;

import java.util.HashMap;
import java.util.Map;

public class InteractionHandUtil {

    public static Map<String, String> SLOT_ID = new HashMap<>(Map.of(
            "ARS_CURIOS_SLOT_1", "ars_curios_slot_1",
            "ARS_CURIOS_SLOT_2", "ars_curios_slot_2",
            "ARS_CURIOS_SLOT_3", "ars_curios_slot_3",
            "ARS_CURIOS_SLOT_4", "ars_curios_slot_4",
            "ARS_SPELL_BOOK", "ars_spell_book"
            ));

    public static boolean isAmmoBox(InteractionHand hand) {
        return hand == InteractionHand.valueOf("ARS_CURIOS_SLOT_1") || hand == InteractionHand.valueOf("ARS_CURIOS_SLOT_2") ||
                hand == InteractionHand.valueOf("ARS_CURIOS_SLOT_3") || hand == InteractionHand.valueOf("ARS_CURIOS_SLOT_4");
    }

    public static boolean isSpellBook(InteractionHand hand) {
        return hand == InteractionHand.valueOf("ARS_SPELL_BOOK");
    }

    public static boolean isCurios(InteractionHand hand) {
        return hand == InteractionHand.valueOf("ARS_CURIOS_SLOT_1") || hand == InteractionHand.valueOf("ARS_CURIOS_SLOT_2") ||
                hand == InteractionHand.valueOf("ARS_CURIOS_SLOT_3") || hand == InteractionHand.valueOf("ARS_CURIOS_SLOT_4") ||
                hand == InteractionHand.valueOf("ARS_SPELL_BOOK");
    }

    public static String getSlotName(InteractionHand hand) {
        return SLOT_ID.containsKey(hand.name()) ? SLOT_ID.get(hand.name()) : hand.name();
    }

    public static InteractionHand getSlotByName(String id) {
        for (InteractionHand hand : InteractionHand.values()) {
            if (hand != null && SLOT_ID.containsKey(hand.name()) && SLOT_ID.get(hand.name()).equals(id)) {
                return hand;
            }
        }

        return InteractionHand.MAIN_HAND;
    }
}

