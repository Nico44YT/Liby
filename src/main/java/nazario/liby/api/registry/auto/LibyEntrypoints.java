package nazario.liby.api.registry.auto;

public enum LibyEntrypoints {
    CLIENT("client"), MAIN("main"), DATA_GEN("data_gen"), SERVER("server");

    private final String name;

    private LibyEntrypoints(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
