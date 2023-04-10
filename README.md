# jdbc_singleton_transacciones


# <h1 style="color:red;">Transacciones</h1>

## Que es ?
es un conjunfo de operaciones sobre la bas e de datos que se deben ejecutar como una unidad de trabajo.

## Concepto
- Varias sentencias se ejecutan en un bloque de ejecucion sobre la base da datos, como si fuera una sola.
- Si alguna de las sentencias falla en su ejecucion podemos dar marcha atras con rollback a todo el bloque de ejecucion.
- Se ejecutan todas las instrucciones o ninguna, pero no que se realicen unas si y otras no.
- si todo se ejecuta correctamente aplicamos los cambios sobre las tablas realizando el commit de todo el bloque ejecutado.

## Pasos para realizar una transaccion JDBC
1. Cambiar la propiedad autocommit de la conexion a false.
2. Si todo sale bien realizamos un commit para guardar los cambios en la base de datos.
3. Si falla alguna sentencia realizamos un rollback para revertir los cambios.
4. Al cerrar la conexion se realiza un commit automaticamnte (en caso de que no se haya realizado antes), inclusive si la propiedad autocommit es false.

## Uso en el codigo

``` java
try(Connection conn = getConnection()) {
  conn.setAutoCommit(false);
  // se ejecuta un conjunto de sentencias 
  // como un bloque: select, update, insert, delete ...
  
  conn.commit();
} catch(SQLException e) {
e.pirntStackTrance();
getConnection().rollback();
}

```
