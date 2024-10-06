## How to Add Liby to Your Project

**Step 1: Add the CurseMaven Repository**
To download Liby from CurseMaven, you first need to add the repository to your ```build.gradle``` file:
```gradle
repositories {
    maven {
        url "https://cursemaven.com"
    }
}
```
**Step 2: Add the Liby Dependency**
1. Find the Correct Maven Snippet:
- Go to the [CurseForge project page](https://www.curseforge.com/minecraft/mc-mods/liby) for Liby.
- Navigate to the Files section and find the version compatible with your Minecraft setup.
- Expand the Curse Maven Snippet for the version you need.
- Copy the provided Maven snippet.

2. Add the Dependency:
- After copying the snippet, add it to the dependencies block in your build.gradle. Here’s an example:
```gradle
dependencies {
    // Add the Liby dependency using the Maven snippet
    modImplementation "curse.maven:liby-1115157:5783866"

    // Liby dependency
    implementation "org.reflections:reflections:0.10.2"
}

```




## How to Use Liby

To utilize the Liby library for registering your mod's elements, follow these steps:

Define the Package for Registries: Start by specifying the package where Liby will search for your registries.


```java
package group.your_fabric_mod;

import net.fabricmc.api.ModInitializer;
import nazario.liby.registry.auto.LibyRegistryLoader;
import net.minecraft.util.Identifier;

public class YourFabricMod implements ModInitializer {

    public static final String MOD_ID = "your_fabric_mod";

    @Override
    public void onInitialize() {
        LibyRegistryLoader.load("group.your_fabric_mod.registry");
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }
}

```

**Set Up Your Registry: Next, create a registry for blocks.**


```java
package group.your_fabric_mod.registry;

//This tells Liby to register this class
@LibyAutoRegister
public class BlockRegistry {
    public static final LibyBlockRegister REGISTER = new LibyBlockRegister(YourFabricMod.MOD_ID);

    public static Block BASIC_BLOCK = REGISTER.registerBlock("basic_block", new Block(AbstractBlock.Settings.create()));
    public static Block BLOCK_WITH_ITEM = REGISTER.registerBlock("block_with_item", new Block(AbstractBlock.Settings.create()), new Item.Settings());

    public static void register() {
     
    }
}
```
**Registering Items: Register items similarly to blocks.**
```java
package group.your_fabric_mod.registry;

@LibyAutoRegister
public class ItemRegistry {
    public static final LibyItemRegister REGISTER = new LibyItemRegister(YourFabricMod.MOD_ID);

    public static Item BASIC_ITEM = REGISTER.registerItem("basic_item", new Item(new Item.Settings()));

    public static void register() {
    
    }
}
```


**Setting Registration Priority: If you want blocks to register before items, adjust the ```@LibyAutoRegister``` annotation as follows:**
```java
@LibyAutoRegister(priority = 1)
```
Higher priority values ensure that items are registered after blocks.


**Handling Item Groups: Liby has a specific way for managing item groups.**
```java
@LibyAutoRegister(priority = 2)
public class ItemGroupRegistry {

    public static final LibyItemGroupRegister REGISTER = new LibyItemGroupRegister(ResearchFrontiers.MOD_ID);

    public static final LibyItemGroup ITEM_GROUP = new LibyItemGroup("group", Text.translatable("your_fabric_mod.itemGroup"), new ItemStack(ItemRegistry.BASIC_ITEM));


   public static void register() {
       ITEM_GROUP.addItem(ItemRegistry.BASIC_ITEM, BlockRegistry.BLOCK_WITH_ITEM);

       REGISTER.registerItemGroups(ITEM_GROUP);
   }
}
```

Since the item group has an icon, ensure items are registered first by setting the priority of ```ItemGroupRegistry``` to 2.



### Creating Multiblocks with Liby

Liby provides a simple way for creating multiblocks. To get started, you need to define a BlockEntityRegistry.

**Step 1: Define the Block Entity Registry**

Create a class for your block entities and use the ```@LibyAutoRegister``` annotation to register it.
```java
@LibyAutoRegister(priority = 3)
public class BlockEntityRegistry {
    public static final LibyBlockEntityRegister REGISTER = new LibyBlockEntityRegister(ResearchFrontiers.MOD_ID);

    public static BlockEntityType<WorkbenchBlockEntity> WORKBENCH_BLOCK_ENTITY = 
        REGISTER.registerType("workbench", BlockEntityType.Builder.create(WorkbenchBlockEntity::new, BlockRegistry.WORKBENCH).build());

    public static void register() {
        // Additional registration logic can be added here
    }
}
```
**Step 2: Create the Workbench Block**

Define the ```WorkbenchBlock``` class, which extends ```LibyMultiBlock```. Here, you initialize an array of BlockPos to define the positions that make up the multiblock structure.
(Due to minecraft limitations on block model sizes 3x3x3, you should consider using GeckoLib for the model)
```java
public class WorkbenchBlock extends LibyMultiBlock {
    public WorkbenchBlock(Settings settings) {
        super(settings, new BlockPos[]{
            new BlockPos(0, 0, 0), // Parent block position
            new BlockPos(-1, 0, 0)  // Child block position
        });
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(WorkbenchBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WorkbenchBlockEntity(pos, state);
    }
}
```

**Step 3: Create the Workbench Block Entity**

Next, implement the ```WorkbenchBlockEntity``` class, which extends ```LibyMultiBlockEntity```. This class represents the block entity associated with the ```WorkbenchBlock```.
```java
public class WorkbenchBlockEntity extends LibyMultiBlockEntity {
    public WorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WORKBENCH, pos, state);
    }
}
```
**Key Points**
- In the ```WorkbenchBlock``` class, the BlockPos array specifies the positions of the blocks that make up the multiblock structure. The first entry should always represent the parent block.
- Multiblock structures are from the start horizontal directional.

**Step 4: Player interactions**

Now we want the player to be able to right click our Workbench, for that you need to override ```onMultiBlockUse```, inside ```WorkbenchBlock```.
```java
    @Override
    public ActionResult onMultiBlockUse(World world, BlockState clickedState, BlockState parentState, BlockPos clickedPos, BlockPos parentPos, PlayerEntity player, BlockHitResult hit) {
        if(world.isClient) return ActionResult.PASS;
        
        player.sendMessage(Text.literal("Hello from Workbench!"));
        
        return ActionResult.SUCCESS;
    }
```




### Additional Information

If you prefer not to name each registration method register, you can specify a custom method name directly in the ```@LibyAutoRegister``` annotation. This allows for more descriptive method names that can enhance code readability and organization.
```java
@LibyAutoRegister(priority = 2, register = "registerItemGroup")
public class ItemGroupRegistry {
    public static void registerItemGroup() {
        // Registration logic for item groups
    }
}

```
