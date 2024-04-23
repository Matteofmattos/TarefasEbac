package FACTORY;

import DOMAIN.Cliente;
import GENERICS.Persistence;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteFactory implements PersistenceFactory {
    public Persistence criaObjeto(){
        String nome = JOptionPane.showInputDialog("Informe o nome do cliente: ").trim();
        String cpf = JOptionPane.showInputDialog("Informe o cpf do cliente: ").trim();
        String endereco = JOptionPane.showInputDialog("Informe a rua do cliente: ").trim();
        String numero = JOptionPane.showInputDialog("Informe o número de residência: ").trim();
        String tel = JOptionPane.showInputDialog("Informe o telefone: ").trim();
        String cidade = JOptionPane.showInputDialog("Informe a cidade: ").trim();
        String uf = JOptionPane.showInputDialog("Informe o estado: ").trim();

        return new Cliente(nome,cpf,tel,endereco,numero,cidade,uf);

    }

    public static Cliente convert(ResultSet rs) {
        Cliente cliente = new Cliente();

        try {
            cliente.setID(rs.getLong("ID_CLIENTE"));
            cliente.setNome(rs.getString(("NOME")));
            cliente.setCpf(String.valueOf(rs.getLong(("CPF"))));
            cliente.setTel(String.valueOf(rs.getLong(("TEL"))));
            cliente.setEnd(rs.getString(("ENDERECO")));
            cliente.setNumero(String.valueOf(rs.getInt(("NUMERO"))));
            cliente.setCidade(rs.getString(("CIDADE")));
            cliente.setEstado(rs.getString(("ESTADO")));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
            System.out.println(e.getErrorCode());
        }
        return cliente;
    }
}
