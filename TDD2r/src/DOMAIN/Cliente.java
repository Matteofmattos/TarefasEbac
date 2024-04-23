package DOMAIN;

import ANOTATIONS.ColunaTabela;
import ANOTATIONS.Tabela;
import ANOTATIONS.TypeKey;
import GENERICS.Persistence;

@Tabela(value = "tb_Clientes")
public class Cliente implements Persistence {
    @ColunaTabela(db_nome = "id", java_nomeSet = "setID")
    private Long id;

    @ColunaTabela(db_nome = "nome", java_nomeSet = "setNome")
    private String nome;
    
    @TypeKey(value = "getCpf")
    @ColunaTabela(db_nome = "cpf", java_nomeSet = "setCpf")
    private String cpf;

    @ColunaTabela(db_nome = "tel", java_nomeSet = "setTel")
    private String tel;

    @ColunaTabela(db_nome = "endereco", java_nomeSet = "setEnd")
    private String end;

    @ColunaTabela(db_nome = "numero", java_nomeSet = "setNumero")
    private String numero;

    @ColunaTabela(db_nome = "cidade", java_nomeSet = "setCidade")
    private String cidade;

    @ColunaTabela(db_nome = "estado", java_nomeSet = "setEstado")
    private String estado;

    public Cliente(String nome, String cpf, String tel, String end, String numero, String cidade, String estado) {
        this.nome = nome;
        this.cpf = cpf;
        this.tel = tel;
        this.end = end;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Cliente() { }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getEnd() {
        return end;
    }
    public void setEnd(String end) {
        this.end = end;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public String getEstado() {
        return this.estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Long getID() { return id;}
    @Override
    public void setID(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id: " + id + ", nome: " + nome + ", cpf: " + cpf + ", tel: " + tel +
                ", end: " + end +
                ", numero: " + numero +
                ", cidade: " + cidade +
                ", uf: " + estado;
    }
}
