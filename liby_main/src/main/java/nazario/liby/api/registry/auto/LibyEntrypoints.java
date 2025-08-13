package nazario.liby.api.registry.auto;

public enum LibyEntrypoints {
    CLIENT("client"),
    MAIN("main"),
    DATA_GEN("data_gen"),
    SERVER("server"),
    ASSET_LOADER("asset_loader"),
    CUSTOM1("custom1"),
    CUSTOM2("custom2");

    private final String name;

    private LibyEntrypoints(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
