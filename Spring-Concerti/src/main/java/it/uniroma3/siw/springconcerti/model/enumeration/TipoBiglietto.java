package it.uniroma3.siw.springconcerti.model.enumeration;

public enum TipoBiglietto {
    
    INTERO("Intero"),
    RIDOTTO("Ridotto");

    private final String nome;

    private TipoBiglietto(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }
    
}
