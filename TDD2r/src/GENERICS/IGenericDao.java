package GENERICS;

import EXCEPTIONS.*;

import java.sql.SQLException;
import java.util.List;

public interface IGenericDao<T extends Persistence> {

    public boolean cadastrar(T entity) throws DaoException, MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception;

    public T consultar(String codigo) throws DaoException, MoreThanOneRegister_Exception,KeyNotFound_Exception, TableException, ElementTypeNotFound_Exception;

    public boolean excluir(String codigo) throws DaoException,KeyNotFound_Exception;

    public boolean alterar(T entity) throws DaoException,TableException,KeyNotFound_Exception, ElementTypeNotFound_Exception;

    public List<T> buscarTodos() throws DaoException;
}