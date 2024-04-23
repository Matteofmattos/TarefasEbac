package DAO;

import DOMAIN.Produto;
import GENERICS.GenericDao;
import GENERICS.IProdutoDao;

import java.sql.PreparedStatement;

public class ProdutosDao extends GenericDao<Produto> implements IProdutoDao {

    public ProdutosDao() {
        super();
    }

    @Override
    public Class<Produto> getTipoClasse() {
        return Produto.class;
    }

    @Override
    protected String getQueryInsertion() {
        return "INSERT INTO tb_Produtos (ID,CODIGO,NOME,DESCRICAO,VALOR) "+
                "VALUES (nextval('sq_idproduto'),?,?,?,?)";
    }

    @Override
    protected String getQueryExclusion() {
        return "DELETE FROM tb_Produtos WHERE CODIGO = ?";
    }
    @Override
    protected String getQueryUpdate() {
        return "UPDATE tb_Produtos SET CODIGO=?, NOME=?, DESCRICAO =?, VALOR=? WHERE CODIGO =?,";
    }

    @Override
    protected void setValuesQueryInsertion(PreparedStatement st, Produto entity) {
        try{
            st.setString(1,entity.getCodigo());
            st.setString(2,entity.getNome());
            st.setString(3,entity.getDescricao());
            st.setBigDecimal(4,entity.getValor());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void setValuesQueryExclusion(PreparedStatement st, String codigo) {
        try {
            st.setString(1,codigo);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    protected void setValuesQuerySelection(PreparedStatement st, String codigo)  {
        try {
            st.setString(1,codigo);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void setValuesQueryUpdate(PreparedStatement st, Produto entity)  {
        try{
            st.setString(1,entity.getCodigo());
            st.setString(2,entity.getNome());
            st.setString(3,entity.getDescricao());
            st.setBigDecimal(4,entity.getValor());
            st.setString(5,entity.getCodigo());

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
