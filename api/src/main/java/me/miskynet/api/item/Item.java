package me.miskynet.api.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public interface Item {

    Item setMaterial(Material material);
    Material getMaterial();

    Item setDisplayName(Component displayName);
    Component getDisplayName();

    Item setLore(List<Component> lore);
    List<Component> getLore();

    Item setAmount(int amount);
    Integer getAmount();

    Item setItemMeta(ItemMeta itemMeta);
    ItemMeta getItemMeta();

    ItemStack toItemStack();
    ItemStack toItemStack(boolean overrideNewMeta);

    Item fromItemStack(ItemStack itemStack);

}
