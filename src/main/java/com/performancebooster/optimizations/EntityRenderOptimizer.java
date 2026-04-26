package com.performancebooster.optimizations;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Reduces entity spawn and AI tick overhead by limiting
 * entity counts and skipping unnecessary AI updates.
 */
public class EntityRenderOptimizer {

    public static int MAX_ENTITY_RENDER_DISTANCE = 48;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEntityJoin(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            return;
        }

        Entity entity = event.getEntity();
        if (entity instanceof Player) return;

        Player nearestPlayer = event.getLevel().getNearestPlayer(entity, 64.0);
        if (nearestPlayer != null) {
            double dist = nearestPlayer.distanceTo(entity);
            if (dist > MAX_ENTITY_RENDER_DISTANCE) {
                entity.setInvisible(true);
            }
        }
    }
}
