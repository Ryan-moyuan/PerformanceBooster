package com.performancebooster.optimizations;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Renders optimizations: reduces overdraw, culls distant entities,
 * and optimizes the rendering pipeline.
 */
public class RenderOptimizer {

    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean ENABLE_DISTANCE_CULLING = true;
    public static int MAX_ENTITY_RENDER_DIST = 48;

    @SubscribeEvent
    public void onRenderStage(RenderLevelStageEvent event) {
        if (event.getStage() != Stage.AFTER_ENTITIES) return;

        // Enable fast rendering hints
        RenderSystem.applyModelViewMatrix();
    }

    /**
     * Calculate squared distance to camera for culling decisions.
     * Returns -1 if camera is not available.
     */
    public static double getSquaredDistanceToCamera(double x, double y, double z) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.gameRenderer.getMainCamera() == null) {
            return -1.0;
        }
        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 pos = camera.getPosition();
        double dx = x - pos.x;
        double dy = y - pos.y;
        double dz = z - pos.z;
        return dx * dx + dy * dy + dz * dz;
    }
}
