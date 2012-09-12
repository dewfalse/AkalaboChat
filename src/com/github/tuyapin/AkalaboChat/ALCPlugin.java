package com.github.tuyapin.AkalaboChat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ALCPlugin extends JavaPlugin
{
  private static ALCPlugin plugin;
  private LoadFiles romaToHiraData;

  public void onEnable()
  {
    plugin = this;

    loadConfiguration();
    plugin.saveConfig();
    
    getCommand("alc").setExecutor(new Commands(this));
    
    
    this.romaToHiraData = new LoadFiles(this);
    new Event(this);
    this.romaToHiraData.a();

    getLogger().info("ALC enabled!");
  }

  public void onDisable()
  {
    plugin.saveConfig();

    getLogger().info("ALC disabled!");
  }
 
  public void reload()
  {
	  this.romaToHiraData = null;
	  this.romaToHiraData = new LoadFiles(this);
	  this.romaToHiraData.a();
	  
	  getLogger().info("ALC Reload!");
  }

  public static ALCPlugin getPlugin()
  {
    return plugin;
  }

  public void disablePlugin() {
    getPluginLoader().disablePlugin(this);
  }


  public LoadFiles getRomaToHiraData() {
    return this.romaToHiraData;
  }

  public String getVersion()
  {
    return getDescription().getVersion();
  }
  
  public void loadConfiguration()
  {
    FileConfiguration config;
    (
      config = plugin.getConfig())
      .options().copyDefaults(true);
    plugin.saveConfig();
  }

  
}