package com.mumu17.arscurios.mixin;

import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.item.IRadialProvider;
import com.hollingsworth.arsnouveau.api.util.StackUtil;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mumu17.arscurios.util.ArsCuriosInventoryHelper;
import com.mumu17.arscurios.util.InteractionHandUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = StackUtil.class, remap = false)
public class StackUtilMixin {

    @Unique
    private static final String ARS_SPELL_BOOK_SLOT = "ars_spell_book";

    @ModifyReturnValue(method = "getHeldSpellbook", at = @At(value = "RETURN"))
    private static ItemStack getHeldSpellbook(ItemStack original, @Local(argsOnly = true) Player playerEntity) {
        if (!(original.getItem() instanceof SpellBook)) {
            return ArsCuriosInventoryHelper.getCuriosInventoryItem(playerEntity, ARS_SPELL_BOOK_SLOT);
        }
        return original;
    }

    @ModifyReturnValue(method = "getBookHand", at = @At(value = "RETURN"))
    private static InteractionHand getBookHand(InteractionHand original, @Local(argsOnly = true) Player playerEntity) {
        if (original == null) {
            return InteractionHandUtil.getSlotByName(ARS_SPELL_BOOK_SLOT);
        }
        return original;
    }

    @ModifyReturnValue(method = "getHeldCasterTool(Lnet/minecraft/world/entity/player/Player;Ljava/util/function/Predicate;)Lnet/minecraft/world/InteractionHand;", at = @At(value = "RETURN"))
    private static InteractionHand getHeldCasterTool(InteractionHand original, @Local(argsOnly = true) Player playerEntity) {
        if (original == null) {
            InteractionHand interactionHand = InteractionHandUtil.getSlotByName(ARS_SPELL_BOOK_SLOT);
            if (ArsCuriosInventoryHelper.getCuriosInventoryItem(playerEntity, InteractionHandUtil.getSlotName(interactionHand)).getItem() instanceof SpellBook) {
                return interactionHand;
            }
        }
        return original;
    }

    @ModifyReturnValue(method = "getQuickCaster", at = @At(value = "RETURN"))
    private static InteractionHand getQuickCaster(InteractionHand original, @Local(argsOnly = true) Player playerEntity) {
        if (original == null) {
            InteractionHand interactionHand = InteractionHandUtil.getSlotByName(ARS_SPELL_BOOK_SLOT);
            if (ArsCuriosInventoryHelper.getCuriosInventoryItem(playerEntity, InteractionHandUtil.getSlotName(interactionHand)).getItem() instanceof SpellBook) {
                return interactionHand;
            }
        }
        return original;
    }

    @ModifyReturnValue(method = "getHeldRadial", at = @At(value = "RETURN"))
    private static ItemStack getHeldRadial(ItemStack original, @Local(argsOnly = true) Player playerEntity) {
        if (!(original.getItem() instanceof IRadialProvider)) {
            return ArsCuriosInventoryHelper.getCuriosInventoryItem(playerEntity, ARS_SPELL_BOOK_SLOT);
        }
        return original;
    }

    @ModifyReturnValue(method = "getHeldCasterToolOrEmpty", at = @At(value = "RETURN"))
    private static ItemStack getHeldCasterToolOrEmpty(ItemStack original, @Local(argsOnly = true) Player playerEntity) {
        if (!(original.getItem() instanceof ICasterTool)) {
            return ArsCuriosInventoryHelper.getCuriosInventoryItem(playerEntity, ARS_SPELL_BOOK_SLOT);
        }
        return original;
    }
}
