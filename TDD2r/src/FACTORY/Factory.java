package FACTORY;

public class Factory{

    public static PersistenceFactory createFactory(String opcaoMenuGeral) {
        return switch (opcaoMenuGeral) {
            case "1" -> new ClienteFactory();
            case "2" -> new ProdutoFactory();
            default -> null;
        };
    }

}
