package nazario.liby.internal.assetgen.v1.client.resource_loader;

import nazario.liby.LibyMain;
import nazario.liby.api.assetgen.v1.client.model.obj.LibyObjModel;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class LibyObjResourceLoader implements IdentifiableResourceReloadListener {
    private static LibyObjResourceLoader instance;
    private final HashMap<Identifier, LibyObjModel> modelsMap = new HashMap<>();
    private final Identifier identifier;

    public LibyObjResourceLoader(Identifier id) {
        this.identifier = id;
        instance = this;

        LibyMain.LOGGER.info("[Liby] Created OBJ Resource Loader");
    }

    public synchronized static LibyObjResourceLoader get() {
        return instance;
    }

    public HashMap<Identifier, LibyObjModel> getMap() {
        return modelsMap;
    }


    @Override
    public Identifier getFabricId() {
        return this.identifier;
    }

    @Override
    public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        try{
            LibyMain.LOGGER.info("[Liby-Obj] Starting to load models.");

            CompletableFuture<HashMap<Identifier, LibyObjModel>> objModelMapFuture = CompletableFuture.supplyAsync(() -> {
                String startingPath = "models/liby_obj";
                HashMap<Identifier, LibyObjModel> libyObjModelHashMap = new HashMap<>();

                Map<Identifier, List<Resource>> files = manager.findAllResources(startingPath, Objects::nonNull);
                LibyMain.LOGGER.info("[Liby-Obj] Collected {} files", files.size());

                files.forEach((fileId, fileResourceList) -> {
                    if(fileId.getPath().startsWith(startingPath) && fileId.getPath().endsWith(".obj")) {
                        fileResourceList.forEach(fileResource -> {
                            libyObjModelHashMap.put(fileId, new LibyObjModel(fileId, fileResource));

                            if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
                                LibyMain.LOGGER.info("[Liby-Obj] loaded \"{}\"", fileId);
                            }
                        });
                    }
                });

                return libyObjModelHashMap;
            });

            return objModelMapFuture.thenCompose(synchronizer::whenPrepared).thenAcceptAsync(prepareData -> {
                if(prepareData != null) modelsMap.putAll(prepareData);

                LibyMain.LOGGER.info("[Liby-Obj] Put all obj models into the map.");
            });
        } catch (Exception e) {
            LibyMain.LOGGER.error("[Liby-Obj] An error occurred trying to load obj models");
            e.printStackTrace();
            return null;
        }
    }
}