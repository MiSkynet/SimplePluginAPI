package me.miskynet.plugin.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.UUID;

public class PlayerHead extends Item implements me.miskynet.api.item.PlayerHead {

    private String textureHash;
    private Player owner;

    /**
     * Creates a player head with a texture hash and a display name.
     *
     * @param textureHash The hash of the texture
     * @param displayName The display name of the skull
     * */
    public PlayerHead(String textureHash, Component displayName) {
        super(Material.PLAYER_HEAD, displayName);
        this.textureHash = textureHash;
        this.owner = null;
    }

    /**
     * Creates a player head with an owner and a display name. This
     * will create the head of a player
     *
     * @param owner The owner of the skull as a player
     * @param displayName The display name of the skull
     * */
    public PlayerHead(Player owner, Component displayName) {
        super(Material.PLAYER_HEAD, displayName);
        this.textureHash = null;
        this.owner = owner;
    }

    /**
     * Creates a player head with a texture hash
     *
     * @param textureHash The hash of the texture
     * @see #PlayerHead(String, Component)  PlayerHead
     * */
    public PlayerHead(String textureHash) {
        super(Material.PLAYER_HEAD);
        this.textureHash = textureHash;
        this.owner = null;
    }

    /**
     * Creates a player head with an owner. This
     * will create the head of a player
     *
     * @param owner The owner of the skull as a player
     * @see #PlayerHead(Player, Component)  PlayerHead
     * */
    public PlayerHead(Player owner) {
        super(Material.PLAYER_HEAD);
        this.textureHash = null;
        this.owner = owner;
    }

    /**
     * Sets the texture hash of the current skull
     *
     * @param textureHash The new texture hash
     * @return The player head
     * */
    @Override
    public me.miskynet.api.item.PlayerHead setTexture(String textureHash) {
        this.textureHash = textureHash;
        return this;
    }

    /**
     * Sets the owner of the current skull
     *
     * @param owner The new texture hash
     * @return The player head
     * */
    @Override
    public me.miskynet.api.item.PlayerHead setOwner(Player owner) {
        this.owner = owner;
        return this;
    }

    /**
     * Converts the player head into a {@link ItemStack}.
     * This will prioritize an owner over a texture hash. If you want
     * to make sure you'll end up with the texture hash, set the owner with {@link #setOwner(Player)}
     * to null. This will force to use the texture hash.
     *
     * @return Player head as item stack
     * */
    @Override
    public ItemStack toItemStack() {
        ItemStack itemStack = super.toItemStack();

        // check if the head is a head, if not, return the item stack
        if (itemStack == null || !(itemStack.getItemMeta() instanceof SkullMeta)) {
            return itemStack;
        }

        // if there is an owner, set the profile of the head to the owner
        // else, get the texture hash
        if (owner != null) {
            // set the profile of the player as a component directly on the item
            itemStack.setData(
                    DataComponentTypes.PROFILE,
                    ResolvableProfile.resolvableProfile().name(this.owner.getName()).build()
            );
        }else {

            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

            // create a player profile
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
            PlayerTextures textures = profile.getTextures();

            // get and set the texture one the
            try {
                String fullUrl = "https://textures.minecraft.net/texture/" + this.textureHash;
                textures.setSkin(URI.create(fullUrl).toURL());
            } catch (MalformedURLException e) {
                throw new RuntimeException("Error while creating the texture", e);
            }

            profile.setTextures(textures);
            skullMeta.setPlayerProfile(profile);
            itemStack.setItemMeta(skullMeta);
        }

        return itemStack;
    }
}
