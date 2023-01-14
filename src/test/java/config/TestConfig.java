package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties", //читаем env
        "file:src/test/resources/configs/test.properties", //читаем из файла
})
public interface TestConfig extends Config {

    @Key("deviceHost")
    String deviceHost();
}
