package com.performancebooster;

import com.performancebooster.optimizations.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(PerformanceBooster.MOD_ID)
public class PerformanceBooster {
    public static final String MOD_ID = "performancebooster";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public PerformanceBooster() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(new EntityRenderOptimizer());
        MinecraftForge.EVENT_BUS.register(new ParticleOptimizer());
        MinecraftForge.EVENT_BUS.register(new TickOptimizer());
        MinecraftForge.EVENT_BUS.register(new ChunkOptimizer());
        MinecraftForge.EVENT_BUS.register(new RenderOptimizer());

        LOGGER.info("Performance Booster loaded - FPS optimization active!");
    }
}
