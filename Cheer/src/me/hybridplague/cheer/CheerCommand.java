package me.hybridplague.cheer;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class CheerCommand implements CommandExecutor {
	Map<String, Long> cooldowns = new HashMap<String, Long>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;

		if (!p.hasPermission("cheer.use")) return false;
		
		if (args.length == 0) {
			p.sendMessage("/cheer <stratham|redmont|harlon>");
			return true;
		} else {
			switch(args[0].toUpperCase()) {
			case "STRATHAM", "REDMONT", "HARLON":
				if (cooldowns.containsKey(p.getName())) {
					if (cooldowns.get(p.getName()) > System.currentTimeMillis()) {
						long timeleft = (cooldowns.get(p.getName()) - System.currentTimeMillis()) / 1000;
						p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "» " + ChatColor.WHITE + "This command has a cooldown. Please wait " + ChatColor.YELLOW + "" + timeleft + ChatColor.WHITE + " more seconds.");
						return true;
					}
				}
				cooldowns.put(p.getName(), System.currentTimeMillis() + (60 * 1000));
				spawnFirework(args[0].toUpperCase(), p.getLocation());
				break;
			default:
				p.sendMessage("/cheer <stratham|redmont|harlon>");
				break;
			}
		}
		return true;
	}
	
	
	public Firework spawnFirework(String team, Location loc) {
		Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fireworkMeta = firework.getFireworkMeta();
		
		Color color = null;
		
		switch(team) {
		case "STRATHAM":
			color = Color.ORANGE;
			break;
		case "REDMONT":
			color = Color.BLUE;
			break;
		case "HARLON":
			color = Color.GREEN;
			break;
		}
		
		FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(color).withFade(color).with(Type.BALL_LARGE).trail(true).build();
		fireworkMeta.addEffect(effect);
		fireworkMeta.setPower(2);
		firework.setFireworkMeta(fireworkMeta);
		
		return firework;
	}
	
	
}
