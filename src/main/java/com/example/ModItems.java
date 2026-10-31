package com.example;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    // 1. создание
    public static final Item WATERING_CAN =
            new WateringCanItem(new Item.Settings()
            .maxCount(1)          // Лейка не должна стакаться (например, 1 штука в руке)
            .maxDamage(250)       // Даём ей прочность в 250 использований
            .rarity(Rarity.COMMON)); // Делаем название жёлтым (необязательно));

    public static void register() {
        // 2. регистрация предмета
        Registry.register(Registries.ITEM, new Identifier("leica-mod", "watering_can"), WATERING_CAN);

        // 3. добавление в инвентарь в креативе
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(WATERING_CAN));
    }


    public static void initialize() {
    }
}