package nazario.liby.api.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LibyItemGroup {
    protected ItemGroup.Builder builder;
    protected List<ItemStack> entriesList;

    public LibyItemGroup(ItemGroup.Builder builder) {
        this.builder = builder;
        this.entriesList = new LinkedList<>();
    }

    public LibyItemGroup(Text displayName) {
        this(FabricItemGroup.builder());
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

    public LibyItemGroup setBackground(String texture) {
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
}