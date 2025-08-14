Liby version 3 brings many new additions and now the library is split up into multiple modules for you to choose from:

* `liby_main`
  Liby Main is the bases for everything, it provides the registry helpers and the automatic registry calling,
  it also provides useful inject into vanilla classes, to get the id of an item/block with `liby$getId()` for example.

* `liby_assetgen`
  Liby AssetGen as the name implies is all about asset generation on runtime, you can add models, blockstates and even textures while the game is running,
  it also allows you to set a predicate for an item model, so you can have different models for the in hand item and the gui item.
  It also features the custom model format for more than 22.5° rotation in models, and all axis rotation for blockstates

* `liby_animations_v2`
  Liby Animations v2 is about animations, but not in the tradition sense of animating an object with bones, it's more of a time procedure that plays
  for example if you want something to happen after 5 seconds and in that time you want to have some particle effects or sounds playing, then a liby animation is the thing you need.

* `liby_animations`
  This was the predecessor of v2, it uses cardinal components for saving under the hood, you can achieve the same effects with this one, but its use is not recommended since it's deprecated.

* `liby_networking`
  Liby Networking is all about network, it gives you a simple annotation to sync a value, (an integer, blockpos or itemstack) in a blockentity (EXPERIMENTAL / W.I.P)

* `liby_ui`
  Liby UI is all about simple screen sync with the server, it automatically sends the state of the screen to the server. (EXPERIMENTAL / W.I.P)

[![](https://jitpack.io/v/Nico44YT/Liby.svg)](https://jitpack.io/#Nico44YT/Liby)

# Liby Main

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
        LibyRegistryLoader.load("group.your_fabric_mod", LOGGER, LibyEntrypoints.MAIN);
    }
}
```
This will check every class inside of `group.your_fabric_mod` and every subclass.

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

public class ModBlocks {
    @LibyAutoRegisterMethod(entrypoint = LibyEntrypoints.MAIN)
    public static void register() {
     
    }
}
```

LibyEntrypoints has 6 possible values
* `MAIN`
* `CLIENT`
* `DATA_GEN`
* `SERVER`
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

public class ModBlocks {
    private static final LibyBlockRegistry REGISTRY = LibyBlockRegistry.of(YourFabricMod.MOD_ID);

    public static final Block TEST_BLOCK = REGISTRY.registerBlock("test_block", new Block(AbstractBlock.Settings.copy(Blocks.DIRT)));

    @LibyAutoRegisterMethod(entrypoint = LibyEntrypoints.MAIN)
    public static void register() {
     
    }
}
```

Each type has its own interface (e.g., `LibyItemRegistry`, `LibyBlockRegistry`) with factory accessors like `LibyBlockRegistry.of(namespace)`.

# Liby AssetGen

## Liby Model Format
The liby model format allows you to have three axis rotation that is not bound to Minecraft's 22.5° turns.

For starters, we need to define the format, that is as simple as just adding a property `format` with the value `liby_v1`
```json
{
    "format": "liby_v1"
}
```

Another key difference between a normal minecraft model and a liby model is the rotation property of an element.
```json
{
    "format": "liby_v1",
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
  "format": "liby_v1",
  "variants": {
    "": {
      "model": "your_fabric_mod:block/test_block",
      "rotation": {
        "x": 35,
        "y": 45,
        "z": 10
      }
    }
  }
}
```

## Liby Asset Registry

To start we need to create a new instance of an `LibyAssetRegistry` in your client class for example.

```java
package group.your_fabric_mod.client;

import nazario.liby.api.assetgen.v1.client.LibyAssetRegistry;
import net.fabricmc.api.ClientModInitializer;

public class YourFabricModClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        LibyAssetRegistry assetRegistry = LibyAssetRegistry.of("your_fabric_mod");
    }
}
```

### Generating Blockstates
```java
    @Override
    public void onInitializeClient() {
        LibyAssetRegistry assetRegistry = LibyAssetRegistry.of("your_fabric_mod");
    }
```

### Adding a variant blockstate

A variant blockstate is commonly used for simple models like a redstone lamp, where you have two distinct states, lamp on and lamp off.

```java
    @Override
    public void onInitializeClient() {
        LibyAssetRegistry assetRegistry = LibyAssetRegistry.of("your_fabric_mod");
        
        // This will create a new variant blockstate for our test block we created prior.
        assetRegistry.registerBlockState(
                new LibyVariantBlockState(ModBlocks.TEST_BLOCK)
                        .addState("power=true",  Identifier.of("your_fabric_mod", "block/test_block_on"))
                        .addState("power=false", Identifier.of("your_fabric_mod", "block/test_block_off"))
        );
    }
```

It can also be used for directional models

```java
    @Override
    public void onInitializeClient() {
        LibyAssetRegistry assetRegistry = LibyAssetRegistry.of("your_fabric_mod");
        
        assetRegistry.registerBlockState(
                new LibyVariantBlockState(ModBlocks.TEST_BLOCK)
                        .addState("facing=north", Identifier.of("your_fabric_mod", "block/test_block"), new Vector3d(0, 0, 0))
                        .addState("facing=east",  Identifier.of("your_fabric_mod", "block/test_block"), new Vector3d(0, 90, 0))
                        .addState("facing=south", Identifier.of("your_fabric_mod", "block/test_block"), new Vector3d(0, 180, 0))
                        .addState("facing=west",  Identifier.of("your_fabric_mod", "block/test_block"), new Vector3d(0, 270, 0))
        );
    }
```

```java
.addState(
    "north=true", // The state when the model should be applied
    Identifier.of("your_fabric_mod" "block/fence_post"), // The model that gets applied
    new Vector3d(0, 0, 0), // The rotation of the model in (x, y, z)
    false // If the texture of the model should be uv locked
)
```

### Adding a multipart blockstate
```java
    @Override
    public void onInitializeClient() {
    LibyAssetRegistry assetRegistry = LibyAssetRegistry.of("your_fabric_mod");


    final Identifier fenceSideModel = Identifier.of("your_fabric_mod", "block/fence_side");
        
        registryAccess.registerBlockState(
                new LibyMultipartBlockState(ModBlocks.FENCE_BLOCK)
                        .addState(null, Identifier.of("your_fabric_mod", "block/fence_post"))
                        .addState("north=true", fenceSideModel, new Vector3f(0, 0, 0),   false)
                        .addState("east=true",  fenceSideModel, new Vector3f(0, 90, 0),  false)
                        .addState("south=true", fenceSideModel, new Vector3f(0, 180, 0), false)
                        .addState("when=true",  fenceSideModel, new Vector3f(0, 0, 0),   false)
        );
    }
```

### Injecting Models

