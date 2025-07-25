package com.mumu17.arscurios.util;


import net.minecraft.world.InteractionHand;

import java.util.Optional;

public enum ExtendedHand {
    MAIN_HAND(InteractionHand.MAIN_HAND),
    OFF_HAND(InteractionHand.OFF_HAND),
    ARS_CURIOS_SLOT_1(null),
    ARS_CURIOS_SLOT_2(null),
    ARS_CURIOS_SLOT_3(null),
    ARS_CURIOS_SLOT_4(null);

    private final InteractionHand vanillaHand;

    ExtendedHand(InteractionHand vanillaHand) {
        this.vanillaHand = vanillaHand;
    }

    public InteractionHand getVanillaHand() {
        return this.vanillaHand;
    }

    public boolean isCurios() {
        return this == ARS_CURIOS_SLOT_1 || this == ARS_CURIOS_SLOT_2 || this == ARS_CURIOS_SLOT_3 || this == ARS_CURIOS_SLOT_4;
    }

    public String getSlotName() {
        return switch (this) {
            case ARS_CURIOS_SLOT_1 -> "ars_curios_slot_1";
            case ARS_CURIOS_SLOT_2 -> "ars_curios_slot_2";
            case ARS_CURIOS_SLOT_3 -> "ars_curios_slot_3";
            case ARS_CURIOS_SLOT_4 -> "ars_curios_slot_4";
            default -> this.name();
        };
    }

    public static ExtendedHand getSlotByName(String name) {
        return switch (name) {
            case "ars_curios_slot_1" -> ARS_CURIOS_SLOT_1;
            case "ars_curios_slot_2" -> ARS_CURIOS_SLOT_2;
            case "ars_curios_slot_3" -> ARS_CURIOS_SLOT_3;
            case "ars_curios_slot_4" -> ARS_CURIOS_SLOT_4;
            case "off_hand" -> OFF_HAND;
            default -> MAIN_HAND;
        };
    }
}

