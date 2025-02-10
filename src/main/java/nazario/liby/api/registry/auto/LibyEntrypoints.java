package nazario.liby.api.registry.auto;

public enum LibyEntrypoints {
    CLIENT("client"), MAIN("main"), DATA_GEN("data_gen"), SERVER("server"), MODEL_LOADER("model_loader"), CUSTOM1("custom1"), CUSTOM2("custom2");

    private final String name;

    private LibyEntrypoints(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
