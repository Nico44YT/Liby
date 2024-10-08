package nazario.liby.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class LibyItemGroup {
    public String id;
    public ItemGroup.Builder builder;
    public List<ItemStack> entriesList;

    public LibyItemGroup(String id, ItemGroup.Builder builder) {
        this.id = id;
        this.builder = builder;
        this.entriesList = new ArrayList<>();
    }
    public LibyItemGroup(String id, Text displayName) {
        this(id, FabricItemGroup.builder());
        this.builder.displayName(displayName);
    }

    public ItemGroup.Builder getBuilder() {
        return this.builder;
    }

    public LibyItemGroup setIcon(ItemStack stack) {
        this.builder.icon(() -> stack);
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
        this.entriesList.addAll(this.entriesList.reversed().reversed());
        return this;
    }

    public ItemGroup build() {
        return builder.entries((context, entries) -> {
            entries.addAll(this.entriesList);
        }).build();
    }
}
