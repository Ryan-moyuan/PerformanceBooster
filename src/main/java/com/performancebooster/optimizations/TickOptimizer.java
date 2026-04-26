package com.performancebooster.optimizations;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Optimizes server tick performance by throttling unnecessary
 * tile entity updates and reducing tick overhead.
 */
public class TickOptimizer {

    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean THROTTLE_TILE_UPDATES = true;
    public static int TILE_UPDATE_INTERVAL = 2; // Update every N ticks instead of every tick

    private static int tickCounter = 0;
    private static boolean isServerTickPhase = false;

    public static boolean shouldUpdateTileEntity() {
        if (!THROTTLE_TILE_UPDATES) return true;
        return tickCounter % TILE_UPDATE_INTERVAL == 0;
    }

    @SubscribeEvent
    public void onServerTick(TickEvent event) {
        if (event.phase == TickEvent.Phase.START && event.type == TickEvent.Type.SERVER) {
            isServerTickPhase = true;
        }
        if (event.phase == TickEvent.Phase.END && event.type == TickEvent.Type.SERVER) {
            tickCounter++;
            isServerTickPhase = false;

            // Log performance every 600 ticks (~30 seconds)
            if (tickCounter % 600 == 0) {
                Runtime runtime = Runtime.getRuntime();
                long usedMem = runtime.totalMemory() - runtime.freeMemory();
                double memMB = usedMem / (1024.0 * 1024.0);
                LOGGER.debug("Performance Booster: Memory used: {} MB", String.format("%.1f", memMB));
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent event) {
        if (event.type == TickEvent.Type.CLIENT) {
            ParticleOptimizer.resetParticleCount(tickCounter);
        }
    }
}
