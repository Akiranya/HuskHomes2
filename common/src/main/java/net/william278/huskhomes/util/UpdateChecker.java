package net.william278.huskhomes.util;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class UpdateChecker {

    private final static int SPIGOT_PROJECT_ID = 83767;
    private final Logger logger;
    private final Version currentVersion;

    public UpdateChecker(@NotNull Version currentVersion, @NotNull Logger logger) {
        this.currentVersion = currentVersion;
        this.logger = logger;
    }

    public CompletableFuture<Version> fetchLatestVersion() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                final URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + SPIGOT_PROJECT_ID);
                URLConnection urlConnection = url.openConnection();
                return Version.pluginVersion(new BufferedReader(new InputStreamReader(urlConnection.getInputStream())).readLine());
            } catch (Exception e) {
                logger.log(Level.WARNING, "Failed to fetch the latest plugin version", e);
            }
            return new Version();
        });
    }

    public boolean isUpdateAvailable(@NotNull Version latestVersion) {
        return latestVersion.compareTo(currentVersion) > 0;
    }

    public Version getCurrentVersion() {
        return currentVersion;
    }

    public CompletableFuture<Boolean> isUpToDate() {
        return fetchLatestVersion().thenApply(this::isUpdateAvailable);
    }

    public void logToConsole() {
        fetchLatestVersion().thenAccept(latestVersion -> {
            if (isUpdateAvailable(latestVersion)) {
                logger.log(Level.WARNING, "A new version of HuskHomes is available: v" + latestVersion);
            } else {
                logger.log(Level.INFO, "HuskHomes is up-to-date! (Running: v" + getCurrentVersion().toString() + ")");
            }
        });
    }
}