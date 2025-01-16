package nazario.liby;

import nazario.liby.api.LibyModelLoaderEntrypoint;
import nazario.liby.api.block.rendering.LibyTransparentTextureBlock;
import nazario.liby.api.item.LibyItemRenderOverrider;
import nazario.liby.registry.LibyClientEntityTypeList;
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

public class LibyClientEntry implements ClientModInitializer, LibyModelLoaderEntrypoint {

    @Override
    public void onInitializeClient() {
        LibyClientEntityTypeList.entityRenderList.forEach((entityType, entityRenderer) -> {
            try {
                registerEntityRenderer(entityType, entityRenderer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        for (Block block : Registries.BLOCK) {
            if (block.getClass().isAnnotationPresent(LibyTransparentTextureBlock.class)) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
            }
        }

        HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
            MinecraftClient.getInstance().player.getHandItems().forEach(stack -> {
                if (stack != null) {
                    if (stack.getItem() instanceof LibyItemRenderOverrider customHudRender) {
                        customHudRender.liby$renderItemHud(drawContext, renderTickCounter, stack);
                    }
                }
            });
        });
    }

    private static <E extends Entity> void registerEntityRenderer(EntityType<? extends Entity> entityType, EntityRendererFactory<? extends Entity> rendererFactory) {
        EntityRendererRegistry.register((EntityType<E>) entityType, (EntityRendererFactory<E>) rendererFactory);
    }

    @Override
    public void onLibyModelLoaderInitialize() {

    }
}
