package io.github.dicedpixels.hardcover;

import io.github.dicedpixels.hardcover.config.HardcoverConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;

public class Hardcover implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("hardcover");
	public static final HardcoverConfig CONFIG = HardcoverConfig.getConfig();

	@Override
	public void onInitializeClient() {
		LOGGER.info("Initializing Hardcover...");
	}
}
