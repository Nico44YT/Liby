package nazario.liby.client;

import nazario.liby.internal.assetgen.v1.client.resource_loader.LibyObjResourceLoader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public class LibyAssetGenClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new LibyObjResourceLoader(Identifier.of("liby", "obj_loader")));

        /*
                LibyTexture texture = new LibyTextureBuilder(Identifier.of("test","block/test_block"), 16, 16)
                .load(Identifier.of("test","block/template"))
                .load(Identifier.of("minecraft","block/oak_log"), (x, y, color) -> color.equals(Color.BLACK))
                .build();

        LibyTextureRegistry.get("block").registerTexture("test", texture);

        LibyBlockStateRegistry.get().registerBlockState("test", new LibyVariantBlockState(LibyAssetGenMain.block).addState("", Identifier.of("test","block/test_block")));

        LibyModelRegistry.get().registerModel("test", LibyModelHelper.createFromParent(Identifier.of("test","block/test_block"), Identifier.of("minecraft","block/cube_all"), List.of(
                new Pair<>("all", Identifier.of("test","block/test_block"))
        )));

        LibyAssetRegistry registry = LibyAssetRegistry.of("test");
        registry.registerItemPredicateModel(Items.APPLE, new ModelIdentifier("minecraft", "iron_ingot", "inventory"), (mode, stack, leftHand) -> leftHand);
         */
    }
}