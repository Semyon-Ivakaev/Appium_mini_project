package helper;

import config.ConfigReader;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApkInfoHelper {
    private String apkInfo;

    public ApkInfoHelper() {
        String app = ConfigReader.emulatorConfig.app();
        if (app == null || app.isEmpty()) {
            throw new RuntimeException("No value for key 'app");
        }

        try {
            apkInfo = DeviceHelper.executeSh("aapt dumb badging " + ConfigReader.emulatorConfig.app());
        } catch (IOException | InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getAppPackageFromApk() {
        return findValueFromString(apkInfo,"package: name='\\s*([^']+?)\\s*'");
    }

    public String getAppMainActivity()  {
        return findValueFromString(apkInfo, "launchable-activity: name='\\s*([^']+?)\\s*'");
    }

    private static String findValueFromString(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
