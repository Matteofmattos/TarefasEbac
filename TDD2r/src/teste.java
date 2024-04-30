import ANOTATIONS.ColunaTabela;
import DOMAIN.Cliente;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class teste {

    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Cliente cliente = new Cliente();

        Field field = cliente.getClass().getDeclaredField("situacao");

        ColunaTabela annotation = field.getAnnotation(ColunaTabela.class);
        String dataBase_nomeColuna = annotation.db_nome();
        String javaSetMethod = annotation.java_nomeSet();
        String fieldType = field.getType().getTypeName();

        Method method = cliente.getClass().getMethod(javaSetMethod, field.getType());

        System.out.println(annotation);
        System.out.println(dataBase_nomeColuna);
        System.out.println(javaSetMethod);
        System.out.println(fieldType);
        System.out.println(method);

        System.out.println("Cliente enum: "+Cliente.Situacao.class.getTypeName());
        System.out.println("Entidade method: "+fieldType);

        System.out.println("Iguais? "+Cliente.Situacao.class.getTypeName().equals(fieldType));

        Object invoke = method.invoke(cliente, Cliente.Situacao.BLOQUEADO);

        System.out.println(cliente.getSituacao());
    }
}
