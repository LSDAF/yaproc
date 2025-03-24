package com.lsadf.yaproc;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;
import picocli.CommandLine;

public class YaprocVersionProvider implements CommandLine.IVersionProvider {

  private static final String MANIFEST_PATH = "META-INF/MANIFEST.MF";
  private static final String IMPLEMENTATION_VERSION = "Implementation-Version";
  private static final String GIT_LINK = "Git-Link";
  private static final String PICOCLI = "picocli";
  private static final String UNKNOWN = "Unknown";

  public String[] getVersion() throws IOException {
    Manifest manifest = getManifest();
    String yaprocVersion = manifest.getMainAttributes().getValue(IMPLEMENTATION_VERSION);
    String gitLink = manifest.getMainAttributes().getValue(GIT_LINK);
    String picocliVersion = getPicocliVersion();
    return new String[] {
      "yaproc " + yaprocVersion + " (picocli build " + picocliVersion + ")",
      "GitHub URL: " + gitLink
    };
  }

  private Manifest getManifest() throws IOException {
    // Get the manifest resource from the JAR
    InputStream manifestStream = getClass().getClassLoader().getResourceAsStream(MANIFEST_PATH);
    if (manifestStream == null) {
      throw new IOException("Manifest file not found in the classpath.");
    }

    return new Manifest(manifestStream);
  }

  private String getPicocliVersion() {
    try {
      // Access the Picocli package and get the version using reflection
      Package picocliPackage = Package.getPackage(PICOCLI);
      return picocliPackage != null ? picocliPackage.getImplementationVersion() : UNKNOWN;
    } catch (Exception e) {
      return UNKNOWN;
    }
  }
}
