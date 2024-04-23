package DAO;
import DOMAIN.ProdutoQuantidade;
import DOMAIN.Venda;
import EXCEPTIONS.DaoException;
import EXCEPTIONS.TableException;
import FACTORY.ProdutoQuantidadeFactory;
import FACTORY.VendaFactory;
import GENERICS.GenericDao;
import GENERICS.IVendaDao;
import JDBC.ConnectionFactory;
import javax.swing.*;
import java.sql.*;
import java.util.*;

import static JDBC.ConnectionFactory.getConnection;

public class VendaDao extends GenericDao<Venda> implements IVendaDao {

    public VendaDao() {
        super();
    }

    public ProdutoQuantidade produtoQuantidade;

    @Override
    public Class<Venda> getTipoClasse() {
        return Venda.class;
    }

    @Override
    protected String getQueryInsertion(){
        return "INSERT INTO tb_Vendas (id,codigo,id_cliente_fk," +
                "valor_total,data_venda,status_venda) " +
                "VALUES(nextval('sq_venda'),?,?,?,?,?)";
    }

    @Override
    protected String getQueryExclusion() {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    @Override
    protected String getQueryUpdate() {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    @Override
    protected void setValuesQueryInsertion(PreparedStatement st, Venda entity) {
        try {
            st.setString(1,entity.getCodigo());
            st.setLong(2,entity.getCliente().getID());
            st.setBigDecimal(3,entity.getValorTotal());
            st.setTimestamp(4, Timestamp.from(entity.getDataVenda()));
            st.setString(5,entity.getStatus().name());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    @Override
    protected void setValuesQueryExclusion(PreparedStatement st, String codigo) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    @Override
    protected void setValuesQuerySelection(PreparedStatement st, String codigo) {
        try {
            st.setString(1,codigo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    @Override
    protected void setValuesQueryUpdate(PreparedStatement st, Venda entity) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    @Override
    public boolean cadastrar(Venda venda) throws DaoException {

        Connection connection = null;
        PreparedStatement st = null;

        try {
            connection = getConnection();
            st = connection.prepareStatement(getQueryInsertion(), Statement.RETURN_GENERATED_KEYS);
            setValuesQueryInsertion(st, venda);

            if (st.executeUpdate() > 0) {

                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) venda.setID(rs.getLong(1));
                }

                for (ProdutoQuantidade prod : venda.getProdutosQtd()) {
                    st = connection.prepareStatement(getQueryProdutosQuantidadeInsertion(), Statement.RETURN_GENERATED_KEYS);
                    setParametersQueryInsertion_ProdutoQuantidade(st, venda, prod);
                    st.executeUpdate();
                }
            }
            return true;

        } catch (SQLException e) {
            throw new DaoException("ERRO CADASTRANDO OBJETO ", e);
        } finally {
            closeConnection(connection, st, null);
        }
    }

    @Override
    public boolean excluir(String valor) {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    public boolean atualizarVenda(Venda vendaC) throws DaoException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryUpdateVenda());
            setValuesQueryUpdateVenda(statement,vendaC);

            if (statement.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"Valor total de venda foi atualizado!");
                return true;
            }

        } catch (SQLException e) {
            throw new DaoException("ERRO ALTERANDO OBJETO ", e);
        } finally {
            closeConnection(connection,statement, null);
        }
        return false;
    }


    @Override
    public String getTableNome() throws TableException {
        return super.getTableNome();
    }

    private static void closeConnection(Connection connection, PreparedStatement statement, ResultSet rs) {
        try {
            if (rs!=null && !rs.isClosed()){
                rs.close();
            }
            if (statement!=null && !statement.isClosed()){
                statement.close();
            }
            if (connection!=null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    private StringBuilder sqlBaseSelect() {

        return new StringBuilder("select v.id as id_venda, v.codigo, v.valor_total,v.data_venda,v.status_venda," +
                "C.ID AS ID_CLIENTE, C.NOME, C.CPF, C.TEL, C.ENDERECO, C.NUMERO, C.CIDADE, C.ESTADO from tb_Vendas as v inner join" +
                " tb_Clientes C on v.id_cliente_fk = C.id ");
    }

    public String getQueryProdutosQuantidadeInsertion(){
        return "INSERT INTO tb_produto_quantidade (ID, ID_PRODUTO_FK, ID_VENDA_FK, QUANTIDADE, VALOR_TOTAL)"+
                "VALUES (nextval('sq_produto_quantidade'),?,?,?,?)"; //
    }

    public void setParametersQueryInsertion_ProdutoQuantidade(PreparedStatement stm, Venda venda, ProdutoQuantidade prodQtd){
        try {
            stm.setLong(1, prodQtd.getProduto().getID());
            stm.setLong(2, venda.getID());
            stm.setInt(3, prodQtd.getQuantidade());
            stm.setBigDecimal(4, prodQtd.getValorTotal());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage()); //
        }
    }

    public String getQueryProdutosQuantidadeUpdate(){
        return "UPDATE tb_produto_quantidade set ID_PRODUTO_FK=?, ID_VENDA_FK=?, QUANTIDADE=?, VALOR_TOTAL=?";
    }

    public void setParametersQueryUpdate_ProdutoQuantidade(PreparedStatement stm, Venda venda, ProdutoQuantidade prodQtd){
        try {
            stm.setLong(1, prodQtd.getProduto().getID());
            stm.setLong(2, venda.getID());
            stm.setInt(3, prodQtd.getQuantidade());
            stm.setBigDecimal(4, prodQtd.getValorTotal());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage()); //
        }
    }

    @Override
    public Venda consultar(String valor) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Venda vendaC = null;

        String query = " where v.codigo = ?;";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sqlBaseSelect()+query);
            statement.setString(1,valor);

            rs = statement.executeQuery();
            while (rs.next()){
                vendaC = VendaFactory.convert(rs);
                associacaoVendaProdutosQuantidade(connection,vendaC);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

        return vendaC;
    }

    public static boolean limparDadosDataBase(String codigoVenda) {
        String exclusionQuery = "delete from tb_vendas where codigo = ?";
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(exclusionQuery);
            st.setString(1,codigoVenda);

            if (st.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"Dados da venda "+codigoVenda+" excluídos com êxito.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null,"Ocorreu um erro ao excluir os registros de venda");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        } finally {
            closeConnection(connection,st,null);
        }

        return false;
    }

    @Override
    public boolean finalizarVenda(Venda venda) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "update tb_vendas set status_venda = ? where id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Venda.Status.CONCLUIDA.name());
            preparedStatement.setLong(2,venda.getID());

            if (preparedStatement.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"VENDA FINALIZADA!");
                return true;
            } else JOptionPane.showMessageDialog(null,"Ocorreu um erro inesperado!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        } finally {
            closeConnection(connection,preparedStatement,resultSet);
        }
        return false;
    }

    @Override
    public boolean cancelarVenda( Venda venda) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "update tb_vendas set status_venda = ? where id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Venda.Status.CANCELADA.name());
            preparedStatement.setLong(2,venda.getID());

            if (preparedStatement.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"VENDA CANCELADA!");
                return true;
            } else JOptionPane.showMessageDialog(null,"Ocorreu um erro inesperado!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        } finally {
            closeConnection(connection,preparedStatement,resultSet);
        }
        return false;
    }

    public void associacaoVendaProdutosQuantidade(Connection connection,Venda venda){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select pq.id as id_produto_quantidade, pq.quantidade,pq.valor_total,p.id as id_PRODUTO ,p.codigo as CODIGO_PRODUTO, p.nome,p.descricao,p.valor as VALOR_UNITARIO " +
                "from tb_produto_quantidade as pq inner join tb_Produtos as p on pq.id_produto_fk= p.id where pq.id_venda_fk =?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,venda.getID());
            resultSet = preparedStatement.executeQuery();
            Set<ProdutoQuantidade> produtoQuantidades = new HashSet<>();
            while (resultSet.next()){
                ProdutoQuantidade convertedProdutoQuantidade = ProdutoQuantidadeFactory.convert(resultSet);
                produtoQuantidades.add(convertedProdutoQuantidade);
            }
            venda.setProdutosQtd(produtoQuantidades);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        } finally {
            closeConnection(connection,preparedStatement,resultSet);
        }
    }

    public List<Venda> buscarTodos(){
        List<Venda> vendas = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlBaseSelect().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Venda convertedVenda = VendaFactory.convert(resultSet);
                associacaoVendaProdutosQuantidade(connection,convertedVenda);
                vendas.add(convertedVenda);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return vendas;
    }

    protected String getQueryUpdateVenda() {
        return "update tb_Vendas set valor_total = ?";
    }

    protected void setValuesQueryUpdateVenda(PreparedStatement stm, Venda vendaC) {
        try {
            stm.setBigDecimal(1,vendaC.getValorTotal());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    public  boolean removerTodosDados(Venda vendaC) {

        String sql =  "delete from tb_vendas where id = ?";
        Connection connection = ConnectionFactory.getConnection();;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1,vendaC.getID());
            int i = statement.executeUpdate();
            if (i>0){
                JOptionPane.showMessageDialog(null,"Todos os dados foram removidos com êxito.");
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Ocorreu um erro ao excluir os dados: "+e.getMessage());
        }
        closeConnection(connection,statement,null);
        return false;
    }
}

