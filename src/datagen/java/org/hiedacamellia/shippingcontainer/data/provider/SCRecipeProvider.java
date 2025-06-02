package org.hiedacamellia.shippingcontainer.data.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import org.hiedacamellia.shippingcontainer.registries.SCItem;

import java.util.concurrent.CompletableFuture;

public class SCRecipeProvider extends RecipeProvider {
    public SCRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SCItem.CONTAINER)
                .pattern("S S")
                .pattern("W W")
                .pattern("SWS")
                .define('S', ItemTags.PLANKS)
                .define('W',ItemTags.WOODEN_SLABS)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(output);


    }
}
