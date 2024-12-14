package nazario.liby.client;

import nazario.liby.block.annotations.BlockCutoutLayer;
import nazario.liby.interfaces.LibyItemRenderOverrider;
import nazario.liby.registry.internal.LibyClientEntityTypeList;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;

public class LibyClientEntry implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LibyClientEntityTypeList.entityRenderList.forEach((entityType, entityRenderer) -> {
            try {
                registerEntityRenderer(entityType, entityRenderer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        for(Block block : Registries.BLOCK) {
            if(block.getClass().isAnnotationPresent(BlockCutoutLayer.class)) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
            }
        }

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient.getInstance().player.getHandItems().forEach(stack -> {
                if(stack != null) {
                    if(stack.getItem() instanceof LibyItemRenderOverrider customHudRender) {
                        customHudRender.renderItemHud(drawContext, tickDelta, stack);
                    }
                }
            });
        });
    }

    private static <E extends Entity> void registerEntityRenderer(EntityType<? extends Entity> entityType, EntityRendererFactory<? extends Entity> rendererFactory) {
        EntityRendererRegistry.register((EntityType<E>) entityType, (EntityRendererFactory<E>) rendererFactory);
    }
}
