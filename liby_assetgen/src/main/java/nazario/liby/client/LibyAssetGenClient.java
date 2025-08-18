package nazario.liby.client;

import nazario.liby.api.assetgen.v1.client.LibyAssetRegistry;
import nazario.liby.api.assetgen.v1.client.model.LibyModelHelper;
import nazario.liby.internal.assetgen.v1.client.model.LibyModelRegistry;
import nazario.liby.internal.assetgen.v1.client.resource_loader.LibyObjResourceLoader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Items;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.List;

public class LibyAssetGenClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new LibyObjResourceLoader(Identifier.of("liby", "obj_loader")));
        //LibyBlockStateRegistry.get().registerBlockState("test", new LibyVariantBlockState(LibyAssetGenMain.block).addState("", Identifier.of("test","block/test_block")));

        LibyModelRegistry.get().registerModel("test", LibyModelHelper.createFromParent(Identifier.of("test","block/test_block"), Identifier.of("minecraft","block/cube_all"), List.of(
                new Pair<>("all", Identifier.of("test","block/test_block"))
        )));

        LibyAssetRegistry registry = LibyAssetRegistry.of("test");
        registry.registerItemPredicateModel(Items.APPLE, ModelIdentifier.ofVanilla("iron_ingot", "inventory"), (mode, stack, leftHand) -> leftHand);

    }
}
