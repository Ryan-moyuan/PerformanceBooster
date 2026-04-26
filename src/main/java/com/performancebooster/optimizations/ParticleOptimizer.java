package com.performancebooster.optimizations;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Reduces particle spawn rate and limits heavy particle types
 * to improve FPS during particle-heavy scenarios.
 */
public class ParticleOptimizer {

    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean REDUCE_PARTICLES = true;
    public static int PARTICLE_LIMIT_PER_TICK = 500;
    public static int PARTICLE_SCALE_FACTOR = 2;

    private static int tickParticleCount = 0;
    private static int currentTick = 0;

    public static boolean shouldSpawnParticle() {
        if (!REDUCE_PARTICLES) return true;
        return tickParticleCount < PARTICLE_LIMIT_PER_TICK;
    }

    public static void resetParticleCount(int tick) {
        if (tick != currentTick) {
            tickParticleCount = 0;
            currentTick = tick;
        }
    }

    public static void incrementParticleCount() {
        tickParticleCount++;
    }

    public static boolean skipHeavyParticle(ParticleOptions type) {
        if (!REDUCE_PARTICLES) return false;
        String name = type.toString();
        // Skip expensive particle types
        return name.contains("smoke") || name.contains("cloud")
            || name.contains("effect") || name.contains("dust");
    }

    @SubscribeEvent
    public void onRegisterParticles(RegisterParticleProvidersEvent event) {
        LOGGER.info("Particle Optimizer: registered particle overrides");
    }
}
