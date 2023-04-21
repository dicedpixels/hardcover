package io.github.dicedpixels.hardcover.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.dicedpixels.hardcover.Hardcover;

import net.fabricmc.loader.api.FabricLoader;

public class HardcoverConfig {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("hardcover.json");

	private static HardcoverConfig CONFIG;

	public boolean alternativeRecipeButton;
	public boolean darkMode;
	public boolean ungroupRecipes;
	public boolean recipeBook = true;
	public boolean unlockAllRecipes;
	public boolean bounce = true;
	public boolean circularScrolling;
	public boolean centeredInventory;
	public boolean mouseWheelScrolling;

	private HardcoverConfig() {}

	public static HardcoverConfig getConfig() {
		if (CONFIG == null) {
			CONFIG = load();
		}
		return CONFIG;
	}

	private static HardcoverConfig load() {
		HardcoverConfig config = null;
		if (Files.exists(PATH)) {
			try (BufferedReader reader = Files.newBufferedReader(PATH)) {
				config = GSON.fromJson(reader, HardcoverConfig.class);
			} catch (Exception e) {
				Hardcover.LOGGER.warn("Hardcover could not load the config file.");
				throw new RuntimeException(e);
			}
		}
		if (config == null) {
			config = new HardcoverConfig();
		}
		return config;
	}

	public void save() {
		try (BufferedWriter writer = Files.newBufferedWriter(PATH)) {
			GSON.toJson(CONFIG, writer);
		} catch (Exception e) {
			Hardcover.LOGGER.warn("Hardcover encountered an error while saving the config file.");
			throw new RuntimeException(e);
		}
	}
}
