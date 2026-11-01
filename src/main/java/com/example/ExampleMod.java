package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	public static final String MOD_ID = "minecraft-mod-I9-23G-leica"; // Новый ID
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		ModItems.register();

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (world.isClient()) return ActionResult.PASS;

			ItemStack stack = player.getStackInHand(hand);
			if (!(stack.getItem() instanceof WateringCanItem)) {
				return ActionResult.PASS;
			}
			if (!player.getAbilities().creativeMode) {
				stack.damage(1, player, (p) -> {

				});
			}
			// Проверяем лейку и цель
			if (player.getStackInHand(hand).getItem() instanceof WateringCanItem
					&& (entity instanceof EndermanEntity || entity instanceof EnderDragonEntity)) {

				// Наносим ДОПОЛНИТЕЛЬНЫЙ урон. Ванильный удар (7 ед.) всё равно произойдёт.
				entity.damage(world.getDamageSources().playerAttack(player), 40.0F);
			}

			return ActionResult.PASS; // Не отменяем ванильную атаку
		});
	}
}