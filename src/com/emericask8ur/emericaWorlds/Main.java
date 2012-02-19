package com.emericask8ur.emericaWorlds;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
  String disabled = "[emericaWorlds] Disabled! Version 1.0";
  String enabled = "[emericaWorlds] Enabled! Version 1.0 By emericask8ur";
  public FileConfiguration config;
  public String configString;
	public static String plugindir = "plugins/emericaWorlds/";
	
  public void onDisable(){
    System.out.println(disabled);
	saveConfig();
  }
  @SuppressWarnings("rawtypes")
  public void onEnable(){
	  saveConfig();
	  List l = config.getList("worlds");
		if (l != null) {
			for (Object o : l) {
				if (o instanceof String) {
					String worldname = (String) o;
					File worldfolder = ((CraftServer) getServer()).getWorldContainer();
					worldfolder = new File(worldfolder, worldname);
					if (worldfolder.exists()) {
						System.out.println("[emericaWorlds] Loading world: " + worldname);
						WorldCreator creator = new WorldCreator(worldname);
						creator.environment(getEnv(worldname));
						World w = creator.createWorld();
						if (w == null) {
							System.out.println("[emericaWorlds] World failed to load!");
						} else {
							System.out.println("[emericaWorlds] World loaded!");
						}
					} else {
						System.out.println("[emericaWorlds] World " + worldname + " no longer exists and has not been loaded!");
					}
				}
			}
		}
	}
	public void saveConfig() {
		ArrayList<String> worldnames = new ArrayList<String>();
		for (World w : getServer().getWorlds()) {
			worldnames.add(w.getName());
		}
		super.saveConfig();
	}

	public Environment getEnv(String worldname) {
		worldname = worldname.toUpperCase();
		for (Environment env : Environment.values()) {
			if (worldname.endsWith("_" + env.toString())) return env;
		}
		return Environment.NORMAL;
	}

  public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args){
    Player p = (Player)sender;
    ChatColor G = ChatColor.GREEN;
    ChatColor Y = ChatColor.YELLOW;
    ChatColor R = ChatColor.RED;
    Server server = p.getServer();
    World w = p.getWorld();
    if (cmdLabel.equalsIgnoreCase("emerica")) {
    	if(p.hasPermission("emerica.menu")){
      sender.sendMessage(ChatColor.BLUE + "Welcome " + p.getName());
      sender.sendMessage(Y + "-----------------emericaWorlds-----------------");
      sender.sendMessage(G + "/WorldGen <Nether/End/Normal> <WorldName>");
      sender.sendMessage(G + "/GoTo <WorldName>");
      sender.sendMessage(G + "/Worlds List");
      sender.sendMessage(G + "/Teleport <Username> <WorldName>");
      sender.sendMessage(G + "/Spawn");
      sender.sendMessage(G + "/SetSpawn");
      sender.sendMessage(G + "/Survival <WorldName>");
      sender.sendMessage(G + "/Creative <WorldName>");
      sender.sendMessage(G + "/PrefixWorld <WorldName>");
      sender.sendMessage(G + "/Set <WorldName> pvp <On/Off>");
      sender.sendMessage(G + "/emericaworlds - Shows Info");
      sender.sendMessage(ChatColor.RED + "/E Help - For Help Menu");
      sender.sendMessage(Y + "----------------------End----------------------");
      return true;
    } else {
    	sender.sendMessage(R + "You do not have permission for this command!");
    	}
    	return true;
    }
    else if (cmdLabel.equalsIgnoreCase("set")) {
    	if(p.hasPermission("emerica.set")){
    	if(args.length==0){
    		sender.sendMessage(R + "Did you mean /Set <WorldName> Pvp <On/Off>");
    	}
    	else if (args.length==1){
    		sender.sendMessage(R + "Did you mean /Set <WorldName> Pvp <On/Off>");
    	}
    	else if (args.length==2){
    		sender.sendMessage(R + "Did you mean /Set <WorldName> Pvp <On/Off>");
    	}
    	else if (args.length==3){
      World wa = server.getWorld(args[0]);
      if (wa != null) {
        if (args[1].equalsIgnoreCase("pvp")) {
          if (args[2].equalsIgnoreCase("on")) {
            wa.setPVP(true);
            sender.sendMessage(ChatColor.YELLOW + "Pvp On in world: " + args[0]);
            return true;
          }
          else if (args[2].equalsIgnoreCase("off")) {
            wa.setPVP(false);
            sender.sendMessage(ChatColor.YELLOW + "Pvp Off in world: " + args[0]);
            return true;
          }
        } else {
          sender.sendMessage("World Not Found!");
        }
      	} else {
      		sender.sendMessage(R + "Did you mean /Set <WorldName> Pvp <On/Off>");
      	}
    	} else {
    		sender.sendMessage(R + "You do not have permission for this command!");
    		}
    	return true;
    	}
      return true;
    }

    else if ((cmdLabel.equalsIgnoreCase("survival")) && (args.length == 1) && (p.hasPermission("emerica.mode"))) {
    	if(args.length==0){
    		sender.sendMessage(R + "Did you mean /Survival <WorldName>");
    	}
      World wa = server.getWorld(args[0]);
      GameMode mode = GameMode.SURVIVAL;
      if (wa != null)
      {
        for (Player pl : wa.getPlayers()) {
          pl.setGameMode(mode);
          sender.sendMessage(G + "Mode to world " + ChatColor.AQUA + args[0] + ChatColor.GOLD + " Has been set to Survival");
        }
      } else sender.sendMessage(ChatColor.RED + "World not found!");

      return true;
    }
    else if ((cmdLabel.equalsIgnoreCase("creative")) && (args.length == 1) && (p.hasPermission("emerica.mode"))) {
      World wa = server.getWorld(args[0]);
      GameMode mode = GameMode.CREATIVE;
      if (wa != null)
      {
        for (Player pl : wa.getPlayers()) {
          pl.setGameMode(mode);
          sender.sendMessage(G + "Mode to world " + ChatColor.AQUA + args[0] + ChatColor.GOLD + " Has been set to Creative");
        }
      }
      else sender.sendMessage(ChatColor.RED + "World not found!");

      return true;
    }

    else if ((cmdLabel.equalsIgnoreCase("goto")) && (p.hasPermission("emerica.goto"))) {
      World world = server.getWorld(args[0]);
      if (world != null) {
        p.teleport(world.getSpawnLocation());
        p.setDisplayName("World: " + ChatColor.AQUA + args[0] + ChatColor.WHITE + "> " + ChatColor.WHITE + p.getName());
      } else {
        sender.sendMessage("Invalid World! Worlds are: " + Bukkit.getServer().getWorlds());
      }
      return true;
    }

    else  if (cmdLabel.equalsIgnoreCase("worldgen") && args.length == 2 && p.hasPermission("emerica.gen")) {
      if (args[0].equalsIgnoreCase("normal")) {
        WorldCreator c = new WorldCreator(args[1]);
        c.environment(World.Environment.NORMAL);
        c.createWorld();
        sender.sendMessage(G + "You have Made a new World! " + ChatColor.AQUA + args[1]);
        return true;
      }
      else if (args[0].equalsIgnoreCase("end")) {
        WorldCreator c = new WorldCreator(args[1]);
        c.environment(World.Environment.THE_END);
        c.createWorld();
        sender.sendMessage(G + "You have Made a new World! " + ChatColor.AQUA + args[1]);
        return true;
      }
      else if (args[0].equalsIgnoreCase("nether")) {
        WorldCreator c = new WorldCreator(args[1]);
        c.environment(World.Environment.NETHER);
        c.createWorld();
        sender.sendMessage(G + "You have Made a new World! " + ChatColor.AQUA + args[1]);
        return true;
      }
      return true;
    }

    else if ((cmdLabel.equalsIgnoreCase("worlds")) && (args.length == 1) && (p.hasPermission("emerica.list"))) {
      if (args[0].equalsIgnoreCase("list")) {
        sender.sendMessage(G + "Worlds: " + ChatColor.AQUA + server.getWorlds());
        return true;
      }
      return true;
    }

    else  if (cmdLabel.equalsIgnoreCase("emericaworlds")) {
      sender.sendMessage(G + "Version 0.4 by emericask8ur, Youtube/emericask8ur");
      return true;
    }

    else if ((cmdLabel.equalsIgnoreCase("setspawn")) && (p.hasPermission("emerica.setspawn"))) {
      w.setSpawnLocation(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
      sender.sendMessage(G + "You have set Spawn Location at X- " + p.getLocation().getBlockX() + " Y- " + p.getLocation().getBlockY() + " Z- " + p.getLocation().getBlockZ());
      return true;
    }

    else  if ((cmdLabel.equalsIgnoreCase("spawn")) && (p.hasPermission("emerica.spawn"))) {
      p.teleport(w.getSpawnLocation());
      sender.sendMessage(G + "You have Teleported to Spawn!");
      return true;
    }

    else if ((cmdLabel.equalsIgnoreCase("teleport")) && (args.length == 2) && (p.hasPermission("emerica.teleport"))) {
      Player pla = server.getPlayer(args[0]);
      World wa = server.getWorld(args[1]);
      if ((pla != null) && (wa != null)) {
        pla.teleport(wa.getSpawnLocation());
        sender.sendMessage(G + "You have Teleported " + ChatColor.AQUA + args[0] + " To World:" + args[1]);
      } else {
        sender.sendMessage(ChatColor.RED + "Invalid World or Player!");
      }
      return true;
    }
    else if ((cmdLabel.equalsIgnoreCase("e")) && (args.length == 1) && (p.hasPermission("emerica.help")) && (args[0].equalsIgnoreCase("help"))) {
      sender.sendMessage(G + "emericaWorlds is a Easy to use World Generator and Teleporter Plugin!");
      sender.sendMessage(G + "Make Sure when you Teleport to a Different World you dont Type /Spawn ! Teleport to the Original World and then Spawn, But if you do want to spawn in that world, you must then SetSpawn!");
      return true;
    }
    return true;
  }
}