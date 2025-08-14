package nazario.liby.api.registry.helper;

import nazario.liby.internal.injections.LibyIdentifierResolvable;
import nazario.liby.internal.registry.LibyImplementableRegistry;
import nazario.liby.internal.registry.LibyImplementedRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public interface LibyTagRegistry extends LibyImplementableRegistry {
    static LibyTagRegistry of(String name) {
        return LibyImplementedRegistry.ofTags(name);
    }

    TagKey<Item> registerItemTag(Identifier tagId, ItemConvertible... items);
    TagKey<Block> registerBlockTag(Identifier tagId, Block... blocks);
    TagKey<Fluid> registerFluidTag(Identifier tagId, Fluid... fluids);
    TagKey<EntityType<?>> registerEntityTypeTag(Identifier tagId, EntityType<?>... entities);
    TagKey<SoundEvent> registerSoundEventTag(Identifier tagId, SoundEvent... soundEvents);
    <T extends LibyIdentifierResolvable> TagKey<T> registerIdentifierResolvable(Identifier tagId, RegistryKey<? extends Registry<T>> registry, T... tagEntries) ;
}
