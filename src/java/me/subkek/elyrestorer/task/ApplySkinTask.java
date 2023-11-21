package me.subkek.elyrestorer.task;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.subkek.elyrestorer.ElyRestorer;
import me.subkek.elyrestorer.type.SkinProperty;
import me.subkek.elyrestorer.type.TaksType;
import me.subkek.elyrestorer.type.Task;
import me.subkek.elyrestorer.utils.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ApplySkinTask extends Task {
    private final ElyRestorer plugin = ElyRestorer.getInstance();
    private final SkinProperty skinProperty;
    private final String playerName;
    private boolean needCallback;
    private Player callbackTo;

    public ApplySkinTask(String playerName, SkinProperty skinProperty, boolean needCallback, Player callbackTo) {
        super(TaksType.APPLY_SKIN);
        this.skinProperty = skinProperty;
        this.playerName = playerName;
        this.needCallback = needCallback;
        this.callbackTo = callbackTo;
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

        float exp = player.getExp();
        player.setExp(0);
        player.setExp(exp);

        if (needCallback) callbackTo.sendMessage(Formatter.format(plugin.language.get("skin-applyed"), true));
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