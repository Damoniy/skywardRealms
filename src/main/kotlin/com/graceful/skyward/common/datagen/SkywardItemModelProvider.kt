package com.graceful.skyward.common.datagen

import com.graceful.skyward.common.Skyward
import com.graceful.skyward.common.item.SkywardItems
import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraftforge.client.model.generators.ItemModelBuilder
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.registries.RegistryObject


class SkywardItemModelProvider(dataGenerator: DataGenerator, existingFileHelper: ExistingFileHelper):
    ItemModelProvider(dataGenerator, Skyward.modId, existingFileHelper) {

    override fun registerModels() {
        simpleItem(SkywardItems.purpleGlowstone)
    }

    private fun simpleItem(item: RegistryObject<Item>): ItemModelBuilder? {
        return withExistingParent(
            item.id.path,
            ResourceLocation("item/generated")
        ).texture(
            "layer0",
            ResourceLocation(Skyward.modId, "item/" + item.id.path)
        )
    }

    private fun handheldItem(item: RegistryObject<Item>): ItemModelBuilder? {
        return withExistingParent(
            item.id.path,
            ResourceLocation("item/handheld")
        ).texture(
            "layer0",
            ResourceLocation(Skyward.modId, "item/" + item.id.path)
        )
    }
}