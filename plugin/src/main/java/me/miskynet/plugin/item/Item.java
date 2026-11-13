package me.miskynet.plugin.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * The {@link Item} is a helper method that makes creating itemStacks easier.
 * After creating an Item, you can convert it into an {@link ItemStack}
 * by simply using {@link #toItemStack()} or {@link #toItemStack(boolean)}.
 * */
public class Item implements me.miskynet.api.item.Item {

    private Material material = null;
    private Component displayName = null;
    private int amount = 0;
    private List<Component> lore = null;
    private ItemMeta itemMeta = null;

    /**
     * Creates an {@link Item} with a set {@link Material} and a given displayName.
     * The material can also later be changed by using {@link #setMaterial(Material)}.
     *
     * @param material The material of the item
     * @param displayName The display name of the item
     * */
    public Item(Material material, Component displayName) {
        this.material = material;
        this.displayName = displayName;
    }

    /**
     * Creates an {@link Item} with a set {@link Material}.
     * The material can also later be changed by using {@link #setMaterial(Material)}.
     *
     * @param material The material of the item
     * @see Item(Material, Component)
     * */
    public Item(Material material) {
        this(material, null);
    }

    /**
     * Sets the {@link Material} of the item.
     *
     * @param material The new material of the item
     * @return The current item
     * */
    @Override
    public me.miskynet.api.item.Item setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Gets the current material of this item.
     *
     * @return {@link Material} of this item
     * */
    @Override
    public Material getMaterial() {
        return this.material;
    }

    /**
     * Sets the display name of this item. The display name is the name
     * of the item (for example when selecting it in the hotbar or when hovering
     * over the item in a container).
     *
     * @param displayName The display name that should be set onto the item
     * @return The current item
     * */
    @Override
    public me.miskynet.api.item.Item setDisplayName(Component displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Gets the current display name of this item.
     *
     * @return Display name as {@link Component}
     * */
    @Override
    public Component getDisplayName() {
        return this.displayName;
    }

    /**
     * Sets the lore of an item. The lore is a list of text that
     * is visible in the tooltip (for example when hovering over an
     * item in a container).
     * */
    @Override
    public me.miskynet.api.item.Item setLore(List<Component> lore) {
        this.lore = lore;
        return this;
    }

    /**
     * Gets the lore of the item.
     *
     * @return List with every line of the lore
     * */
    @Override
    public List<Component> getLore() {
        return this.lore;
    }

    /**
     * Sets the amount of this item on this stack. This can only be positive values.
     *
     * @param amount The amount of the item's on a single stack
     * @return The current item
     * */
    @Override
    public me.miskynet.api.item.Item setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Gets the amount of the current item.
     *
     * @return Amount of item's on this item
     * */
    @Override
    public Integer getAmount() {
        return this.amount;
    }

    /**
     * Sets the {@link ItemMeta} onto this item. Depending on the use
     * of {@link #toItemStack()} and {@link #toItemStack(boolean)} some properties
     * will be overridden with other set values or not.
     * <p></p>
     * For more information, see {@link #setDisplayName(Component)} and {@link #setLore(List)}.
     *
     * @param itemMeta The new item meta
     * @return The current item
     * */
    @Override
    public me.miskynet.api.item.Item setItemMeta(ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * Gets the item meta of the current item. If no item meta was set with
     * {@link #setItemMeta(ItemMeta)}, the {@link #toItemStack()} method will be
     * called and the item meta of the new build item will be returned.
     *
     * @return ItemMeta set with {@link #setItemMeta(ItemMeta)} or created item meta
     * after calling {@link #toItemStack()}
     * */
    @Override
    public ItemMeta getItemMeta() {
        if (this.itemMeta != null) return this.itemMeta;
        return toItemStack().getItemMeta();
    }

    /**
     * Creates an item stack with all set values.
     *
     * @param overrideNewMeta When set to true, override values contained in the
     * item meta with "new" values set with, for example, {@link #setDisplayName(Component)}.
     * @return Item as item stack
     * */
    @Override
    public ItemStack toItemStack(boolean overrideNewMeta) {
        ItemStack itemStack = new ItemStack(this.material);

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (this.itemMeta != null) itemMeta = this.itemMeta;

        // override "new" data written by the item meta, defined with #setItemMeta()
        // (for example, override the displayName set with #setItemMeta() with the one set in #setDisplayName())
        if (overrideNewMeta && this.itemMeta != null) {
            if (this.displayName != null) itemMeta.displayName(this.displayName);
            if (this.amount != 0) itemStack.setAmount(this.amount);
            if (this.lore != null) itemMeta.lore(this.lore);
        }
        itemStack.setItemMeta(itemMeta);

        return null;
    }

    /**
     * Creates an item stack with all set values. This will override values contained in
     * the item meta with "new" values set with, for example, {@link #setDisplayName(Component)}.
     *
     * @return Item as item stack
     * */
    @Override
    public ItemStack toItemStack() {
        return toItemStack(true);
    }

    /**
     * Convert an {@link ItemStack} into an item. This parses all the available values
     * into a new Item. There shouldn't be any data lost when converting
     * an item stack into an item
     *
     * @return Item Stack as item
     * */
    @Override
    public me.miskynet.api.item.Item fromItemStack(ItemStack itemStack) {
        Material material = itemStack.getType();
        Component displayName = itemStack.displayName();
        int amount = itemStack.getAmount();
        List<Component> lore = itemStack.lore();
        ItemMeta meta = itemStack.getItemMeta();

        Item item = new Item(material, displayName);
        item.setAmount(amount);
        item.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
