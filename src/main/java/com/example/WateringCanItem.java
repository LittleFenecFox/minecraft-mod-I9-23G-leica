package com.example;

import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.server.world.ServerWorld;

// двигатель и поведение предмета
public class WateringCanItem extends Item {
    public WateringCanItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        // 1. Проверка: только на сервере (чтобы избежать рассинхрона)
        if (world.isClient()) {
            return ActionResult.PASS;
        }

        // зелёные искорки
        if (world instanceof ServerWorld serverWorld) {
            // Вызываем ванильную частицу "Happy Villager" (зеленые искорки)
            // Параметры: тип частицы, X, Y, Z, количество, скорость (разброс)
            serverWorld.spawnParticles(
                    ParticleTypes.HAPPY_VILLAGER, // Тип частицы
                    pos.getX() + 0.5,             // Центр блока по X
                    pos.getY() + 1.0,             // Чуть выше блока (над грядкой)
                    pos.getZ() + 0.5,             // Центр блока по Z
                    10,                           // Количество частиц (можно увеличить)
                    0.5, 0.5, 0.5,                // Разброс по осям (X, Y, Z)
                    0.0                           // Скорость
            );
        }
        // 2. Условие: является ли блок грядкой (обработанной землёй)?
        if (world.getBlockState(pos).getBlock() instanceof FarmlandBlock) {
            // 3. Эффект: меняем влажность на максимум
            world.setBlockState(pos, world.getBlockState(pos).with(FarmlandBlock.MOISTURE, 7));



            // Возвращаем SUCCESS, чтобы игра поняла, что действие выполнено
            return ActionResult.SUCCESS;
        }

        // Если блок не грядка — ничего не делаем
        return ActionResult.PASS;
    }
}
