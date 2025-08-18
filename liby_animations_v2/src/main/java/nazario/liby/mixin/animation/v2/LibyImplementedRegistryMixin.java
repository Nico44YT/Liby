package nazario.liby.mixin.animation.v2;

import nazario.liby.api.animation.v2.LibyAnimatable;
import nazario.liby.api.animation.v2.LibyAnimation;
import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.registry.LibyImplementedRegistry;
import nazario.liby.api.animation.v2.registry.LibyAnimationRegistry;
import nazario.liby.internal.animation.v2.LibyInternalAnimationRegistry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Predicate;

@Mixin(LibyImplementedRegistry.class)
public abstract class LibyImplementedRegistryMixin implements LibyAnimationRegistry {
   //@Shadow @Final private String namespace;

   //@Override
   //public Identifier registerAnimation(String name, LibyAnimation.Factory animationFactory) {
   //    return LibyInternalAnimationRegistry.registerAnimation(LibyIdentifier.of(this.namespace, name), animationFactory, libyAnimatable -> libyAnimatable instanceof LibyAnimatable);
   //}

   //@Override
   //public Identifier registerAnimation(String name, LibyAnimation.Factory animationFactory, Class<LibyAnimatable> predicateClass) {
   //    return LibyInternalAnimationRegistry.registerAnimation(LibyIdentifier.of(this.namespace, name), animationFactory, predicateClass::isInstance);
   //}

   //@Override
   //public Identifier registerAnimation(String name, LibyAnimation.Factory animationFactory, Predicate<LibyAnimatable> predicate) {
   //    return LibyInternalAnimationRegistry.registerAnimation(LibyIdentifier.of(this.namespace, name), animationFactory, predicate);
   //}

   //@Override
   //public Identifier setAnimation(String name, LibyAnimation.Factory animationFactory) {
   //    return LibyInternalAnimationRegistry.setAnimation(LibyIdentifier.of(this.namespace, name), animationFactory, libyAnimatable -> libyAnimatable instanceof LibyAnimatable);
   //}

   //@Override
   //public Identifier setAnimation(String name, LibyAnimation.Factory animationFactory, Class<LibyAnimatable> predicateClass) {
   //    return LibyInternalAnimationRegistry.setAnimation(LibyIdentifier.of(this.namespace, name), animationFactory, predicateClass::isInstance);
   //}

   //@Override
   //public Identifier setAnimation(String name, LibyAnimation.Factory animationFactory, Predicate<LibyAnimatable> predicate) {
   //    return LibyInternalAnimationRegistry.setAnimation(LibyIdentifier.of(this.namespace, name), animationFactory, predicate);
   //}
}
