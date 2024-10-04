package nazario.liby.registry.helper;

import nazario.liby.item.LibyItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class LibyItemGroupRegister extends LibyRegister {
    public LibyItemGroupRegister(String namespace) {
        super(namespace);
    }

    public ItemGroup registerItemGroup(String id, ItemConvertible icon, Text displayName, ItemConvertible... items) {
        return Registry.register(Registries.ITEM_GROUP,
                Identifier.of(namespace, id),
                FabricItemGroup.builder().icon(() -> new ItemStack(icon))
                        .displayName(displayName)
                        .entries((displayContext, entries) -> {
                            for(ItemConvertible item : items) {
                                entries.add(item);
                            }
                        })
                        .build());
    }

    public ItemGroup registerItemGroup(LibyItemGroup libyItemGroup) {
        return Registry.register(Registries.ITEM_GROUP, Identifier.of(namespace, libyItemGroup.id), libyItemGroup.build());
    }

    public void registerItemGroups(LibyItemGroup... libyItemGroups) {
        for(LibyItemGroup group : libyItemGroups) {
            this.registerItemGroup(group);
        }
    }

    public ItemGroup registerItemGroup(ItemGroup.Builder builder, String id) {
        return Registry.register(Registries.ITEM_GROUP, Identifier.of(namespace, id), builder.build());
    }
}
