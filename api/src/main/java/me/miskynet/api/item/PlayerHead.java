package me.miskynet.api.item;

import org.bukkit.entity.Player;

public interface PlayerHead extends Item {

    PlayerHead setTexture(String textureHash);

    PlayerHead setOwner(Player owner);

}
