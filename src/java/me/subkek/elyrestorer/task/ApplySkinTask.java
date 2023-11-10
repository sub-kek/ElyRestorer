package me.subkek.elyrestorer.task;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.subkek.elyrestorer.ElyRestorer;
import me.subkek.elyrestorer.type.SkinProperty;
import me.subkek.elyrestorer.type.TaksType;
import me.subkek.elyrestorer.type.Task;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ApplySkinTask extends Task {
    private final ElyRestorer plugin = ElyRestorer.getInstance();
    private final SkinProperty skinProperty;
    private final String playerName;

    public ApplySkinTask(String playerName, SkinProperty skinProperty) {
        super(TaksType.APPLY_SKIN);
        this.skinProperty = skinProperty;
        this.playerName = playerName;
    }

    @Override
    public void execute() {
        Player player = Bukkit.getPlayer(playerName);

        if (player == null || !player.isOnline()) return;

        PlayerProfile profile = player.getPlayerProfile();

        profile.getProperties().removeIf(profileProperty -> profileProperty.getName().equals(SkinProperty.TEXTURES_NAME));
        profile.getProperties().add(new ProfileProperty(SkinProperty.TEXTURES_NAME, skinProperty.getValue(), skinProperty.getSignature()));

        player.setPlayerProfile(profile);

        hidePlayer(player);
        showPlayer(player);
    }

    private void hidePlayer(Player player) {
        for (Player sp : Bukkit.getOnlinePlayers()) {
            sp.hidePlayer(plugin, player);
        }
    }

    private void showPlayer(Player player) {
        for (Player sp : Bukkit.getOnlinePlayers()) {
            sp.showPlayer(plugin, player);
        }
    }
}
