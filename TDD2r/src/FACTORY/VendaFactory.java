package FACTORY;

import DOMAIN.Cliente;
import DOMAIN.Venda;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VendaFactory {
    public static Venda convert(ResultSet rs) {
        Cliente cliente = ClienteFactory.convert(rs);
        Venda venda = new Venda();
        venda.setCliente(cliente);
        try {
            venda.setID(rs.getLong("ID_VENDA"));
            venda.setCodigo(rs.getString("CODIGO"));
            venda.setValorTotalVenda(rs.getBigDecimal("VALOR_TOTAL"));
            venda.setDataVenda(rs.getTimestamp("DATA_VENDA").toInstant());
            venda.setStatus(Venda.Status.getStatusByName(rs.getString("STATUS_VENDA")));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
            System.out.println(e.getErrorCode());
        }

        return venda;
    }
}
