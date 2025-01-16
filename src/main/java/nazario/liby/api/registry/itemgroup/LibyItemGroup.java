package nazario.liby.api.registry.itemgroup;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibyItemGroup {
    public Identifier id;
    public ItemGroup.Builder builder;
    public List<ItemStack> entriesList;

    public LibyItemGroup(Identifier id, ItemGroup.Builder builder) {
        this.id = id;
        this.builder = builder;
        this.entriesList = new ArrayList<>();
    }

    public LibyItemGroup(Identifier id, Text displayName) {
        this(id, FabricItemGroup.builder());
        this.builder.displayName(displayName);
    }

    public LibyItemGroup setBuilder(ItemGroup.Builder builder) {
        this.builder = builder;
        return this;
    }

    public ItemGroup.Builder getBuilder() {
        return this.builder;
    }

    public LibyItemGroup setIcon(ItemStack stack) {
        this.builder.icon(() -> stack);
        return this;
    }

    public LibyItemGroup setBackground(Identifier texture) {
        this.builder.texture(texture);
        return this;
    }

    public LibyItemGroup addItem(ItemConvertible... itemConvertibles) {
        for(ItemConvertible convertible : itemConvertibles) {
            this.entriesList.add(new ItemStack(convertible));
        }
        return this;
    }

    public LibyItemGroup addItemStack(ItemStack stack) {
        this.entriesList.add(stack);
        return this;
    }

    public LibyItemGroup addItemStack(ItemStack... stacks) {
        this.entriesList.addAll(Arrays.asList(stacks));
        return this;
    }

    public ItemGroup build() {
        return builder.entries((context, entries) -> {
            entries.addAll(this.entriesList);
        }).build();
    }

    @ApiStatus.Internal
    public void register() {
        Registry.register(Registries.ITEM_GROUP, this.id, this.build());
    }
}