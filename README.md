Liby version 3 brings many new features, like rendering obj models, a custom model and blockstate format and a revamped registration system.

**As of now version 3 has not been released**

[![](https://jitpack.io/v/Nico44YT/Liby.svg)](https://jitpack.io/#Nico44YT/Liby)

## Registry Loading

To start we first need to specify which package should be searched for `@LibyAutoRegister` or `@LibyAutoRegisterMethod`

```java
package group.your_fabric_mod;

import net.fabricmc.api.ModInitializer;
import nazario.liby.api.registry.auto.LibyRegistryLoader;

public class YourFabricMod implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.get("YourFabricMod");
    public static final String MOD_ID = "your_fabric_mod";

    @Override
    public void onInitialize() {
        LibyRegistryLoader.load("group.your_fabric_mod.registry", LOGGER, LibyEntrypoints.MAIN);
    }
}
```

Now in our `ModBlocks.class` we can annotate the entire class with `@LibyAutoRegister`

```java
package group.your_fabric_mod.registry;

//This tells Liby to register this class, 
//it will always assume the method is named "register" if nothing is specified
@LibyAutoRegister
public class ModBlocks {
    public static void register() {
     
    }
}
```

Or we can annotate just the register method with `@LibyAutoRegisterMethod`
```java
package group.your_fabric_mod.registry;

public class ModBlocks {
    @LibyAutoRegisterMethod
    public static void register() {
     
    }
}
```

Both annotations can have an entrypoint specified
```java
package group.your_fabric_mod.registry;

public class BlockRegistry {
    @LibyAutoRegisterMethod(entrypoint = LibyEntrypoints.MAIN)
    public static void register() {
     
    }
}
```

LibyEntrypoints has 7 possible values
* `MAIN`
* `CLIENT`
* `DATA_GEN`
* `SERVER`
* `ASSET_LOADER`
* `CUSTOM1`
* `CUSTOM2`

Here are both full annotations:

```java
@LibyAutoRegister(method = "register", priority = 0, entrypoint = LibyEntrypoints.MAIN)
```
```java
@LibyAutoRegisterMethod(priority = 0, entrypoint = LibyEntrypoints.MAIN)
```

The lower the priority, the earlier the annotation gets called, an annotation with a priority of 0 will get called before one with a priority of 10.

It is also possible to assign multiple entrypoints to one annotation

```java
@LibyAutoRegister(method = "register", priority = 0, entrypoint = {LibyEntrypoints.MAIN, LibyEntrypoints.CLIENT})
```

## Registry Helpers

Liby provides easy-to-use registry helpers for common types (like items, blocks...), located in the `nazario.liby.api.registry.helper` package.

These registry helpers simplify registering content like items, blocks, sounds, and more under your mod’s namespace.

Supported Types
* `Item`
* `Block`
* `BlockEntity`
* `EntityType`
* `Sound`
* `Recipe`
* `Tag`

```java
package group.your_fabric_mod.registry;

public class BlockRegistry {
    private static final LibyBlockRegistry REGISTRY = LibyBlockRegistry.of(YourFabricMod.MOD_ID);

    public static final Block TEST_BLOCK = REGISTRY.registerBlock("test_block", new Block(AbstractBlock.Settings.copy(Blocks.DIRT)));

    @LibyAutoRegisterMethod(entrypoint = LibyEntrypoints.MAIN)
    public static void register() {
     
    }
}
```

Each type has its own interface (e.g., `LibyItemRegistry`, `LibyBlockRegistry`) with factory accessors like `LibyBlockRegistry.of(namespace)`.

## Liby Model Format
The liby model format allows you to have three axis rotation that is not bound to Minecraft's 22.5° turns.

For starters we need to define the format, that is as simple as just adding a property `format` with the value `liby`
```json
{
    "format": "liby"
}
```

Another key difference between a normal minecraft model and a liby model is the rotation property of an element.
```json
{
    "format": "liby",
    "textures": {
        "0": "block/dirt",
        "particle": "block/dirt"
    },
    "elements": [
        {
            "from": [0, 0, 0],
            "to": [16, 16, 16],
            "rotation": {
                "x": 0, 
                "y": 10, 
                "z": 0, 
                "origin": [0, 0, 0]
            },
            "faces": {
                "north": {"uv": [0, 0, 16, 16], "texture": "#0"},
                "east": {"uv": [0, 0, 16, 16], "texture": "#0"},
                "south": {"uv": [0, 0, 16, 16], "texture": "#0"},
                "west": {"uv": [0, 0, 16, 16], "texture": "#0"},
                "up": {"uv": [0, 0, 16, 16], "texture": "#0"},
                "down": {"uv": [0, 0, 16, 16], "texture": "#0"}
            }
        }
    ]
}
```

Alongside the model format is also an accompanying blockbench plugin forked from MrCrayfishs [Open Model Format plugin](https://github.com/MrCrayfish/OpenModelFormat/tree/main)

The plugin can be found [here](https://m.youtube.com/watch?v=dQw4w9WgXcQ)

## Liby Blockstate Format

As like the liby model format, the blockstate format also allows free rotation in all three axis.
```json
{
  "format": "liby",
  "variants": {
    "": {
      "model": "your_fabric_mod:block/test_block",
      "x": 35,
      "y": 45,
      "z": 0
    }
  }
}
```

## Liby Asset Loading Entrypoint (Injecting Blockstates and Models)

To start you need to implement the interface `LibyAssetLoadingEntrypoint` in your client class for example. (Note that asset loading is client side only)
```java
package group.your_fabric_mod.client;

import nazario.liby.api.client.entrypoint.LibyAssetLoadingEntrypoint;
import nazario.liby.api.client.entrypoint.LibyAssetRegistryAccess;
import net.fabricmc.api.ClientModInitializer;

public class YourFabricModClient implements ClientModInitializer, LibyAssetLoadingEntrypoint {

    @Override
    public void onInitializeClient() {

    }

    @Override
    public void onLibyAssetLoading(LibyAssetRegistryAccess registryAccess) {
        
    }
}
```

Now you need to add the entrypoint to your `fabric.mod.json`

```json
"entrypoints": {
    "client": [
      "group.your_fabric_mod.client.YourFabricModClient"
    ],
    "main": [
      "group.your_fabric_mod.YourFabricMod"
    ],
    "liby_asset_loader": [
      "group.your_fabric_mod.client.YourFabricModClient"
    ]
  },
```

### Injecting Blockstates
To inject blockstates we will need the `onLibyAssetLoading` method along with the `registryAccess`

There are two possible ways to have them 1. variant blockstate, which has a model for every possible variant of a block or 2. a multipart blockstate.

An example of a variant blockstate would be the redstone lamp.
An example of a multipart blockstate would be a fence.

```java
.addState(
    "north=true", // The state when the model should be applied
    Identifier.of("your_fabric_mod" "block/fence_post"), // The model that gets applied
    new Vector3f(0, 0, 0), // The rotation of the model in (x, y, z)
    false // If the texture of the model should be uv locked
)
```

### Adding a variant blockstate

```java
    @Override
    public void onLibyAssetLoading(LibyAssetRegistryAccess registryAccess) {
        registryAccess.addBlockState(
                new LibyVariantBlockState(ModBlocks.LAMP_BLOCK)
                        .addState("power=on", Identifier.of("your_fabric_mod", "block/lamp_on"))
                        .addState("power=off", Identifier.of("your_fabric_mod", "block/lamp_off"))
        );
    }
```

### Adding a multipart blockstate
```java
    @Override
    public void onLibyAssetLoading(LibyAssetRegistryAccess registryAccess) {
        final Identifier fenceSideModel = Identifier.of("your_fabric_mod", "block/fence_side");
        
        registryAccess.addBlockState(
                new LibyMultipartBlockState(ModBlocks.FENCE_BLOCK)
                        .addState(null, Identifier.of("your_fabric_mod", "block/fence_post"))
                        .addState("north=true", fenceSideModel, null, false)
                        .addState("east=true", fenceSideModel, new Vector3f(0, 90, 0), false)
                        .addState("south=true", fenceSideModel, new Vector3f(0, 180, 0), false)
                        .addState("when=true", fenceSideModel, new Vector3f(0, 0, 0), false)
        );
    }
```

### Injecting Models

## Utilites
Liby also offers a lot of util methods and classes.

### Nbt Utils

