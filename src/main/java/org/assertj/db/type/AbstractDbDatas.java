package org.assertj.db.type;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.assertj.db.error.AssertJDBException;

/**
 * This class represents data from the database.
 * <p>
 * That could be data from a {@link Table} or from a {@link Request}.<br/>
 * So this class contains : the way to access the database with {@link #getSource()} and {@link #getDataSource()} (one
 * of them need to be set before loading the data).<br/>
 * There are also the list of columns name ({@link #getColumnsNameList()}) and the list of the rows (
 * {@link #getRowsList()}). The first call to one of these methods triggers a loading from the database.
 * </p>
 * 
 * @author Régis Pouiller
 * 
 * @param <E> Class of the subclass (an implementation of {@link AbstractDbDatas}) : useful for the fluent methods
 *          (setters).
 */
public abstract class AbstractDbDatas<E extends AbstractDbDatas<E>> {

  /**
   * Source of the datas.
   */
  private Source source;
  /**
   * Data source.
   */
  private DataSource dataSource;
  /**
   * List of the column names.
   */
  private List<String> columnsNameList;
  /**
   * List of the rows.
   */
  private List<Row> rowsList;

  /**
   * Default constructor.
   */
  public AbstractDbDatas() {
    // empty
  }

  /**
   * Constructor with a {@link Source}.
   * 
   * @param source The {@link Source} to connect to the database (must be not {@code null}).
   * @throws NullPointerException If {@code source} is {@code null}.
   */
  public AbstractDbDatas(Source source) {
    setSource(source);
  }

  /**
   * Constructor with a {@link DataSource}.
   * 
   * @param dataSource The {@link DataSource} (must be not {@code null}).
   * @throws NullPointerException If {@code dataSource} is {@code null}.
   */
  public AbstractDbDatas(DataSource dataSource) {
    setDataSource(dataSource);
  }

  /**
   * Return the source.
   * 
   * @see #setSource(Source)
   * @return The {@link Source} to connect.
   */
  public Source getSource() {
    return source;
  }

  /**
   * Sets the source.
   * 
   * @see #getSource()
   * @param source {@link Source} to connect to the database (must be not {@code null}).
   * @return The actual instance.
   * @throws NullPointerException If {@code source} is {@code null}.
   */
  // Need the @SuppressWarnings because of the cast (E)
  @SuppressWarnings("unchecked")
  public E setSource(Source source) {
    if (source == null) {
      throw new NullPointerException("source must be not null");
    }
    this.source = source;
    this.dataSource = null;
    return (E) this;
  }

  /**
   * Return the data source.
   * 
   * @see #setDataSource(DataSource)
   * @return The data source.
   */
  public DataSource getDataSource() {
    return dataSource;
  }

  /**
   * Sets the data source.
   * 
   * @see #getDataSource()
   * @param dataSource The {@link DataSource} (must be not {@code null}).
   * @return The actual instance.
   * @throws NullPointerException If {@code dataSource} is {@code null}.
   */
  // Need the @SuppressWarnings because of the cast (E)
  @SuppressWarnings("unchecked")
  public E setDataSource(DataSource dataSource) {
    if (dataSource == null) {
      throw new NullPointerException("dataSource must be not null");
    }
    this.source = null;
    this.dataSource = dataSource;
    return (E) this;
  }

  /**
   * Returns the SQL request.
   * 
   * @see Table#getRequest()
   * @see Request#getRequest()
   * @return The SQL request.
   */
  public abstract String getRequest();

  /**
   * Loads the informations of the data from the database.
   * <p>
   * This method gets a {@link Connection} and calls {@link AbstractDbDatas#loadImpl(Connection)} for specific loading
   * depending of being a {@link Table} or a {@link Request}.
   * </p>
   * 
   * @throws NullPointerException If the {@link #dataSource} and {@link #source} fields are {@code null}.
   * @throws AssertJDBException If triggered, this exception wrap a possible {@link SQLException} during the loading.
   */
  private void load() {
    if (dataSource == null && source == null) {
      throw new NullPointerException("connection or dataSource must be not null");
    }

    Connection connection = null;
    try {
      // Get a Connection differently, depending if it is a DataSource or a Source.
      if (dataSource != null) {
        connection = dataSource.getConnection();
      } else {
        connection = DriverManager.getConnection(source.getUrl(), source.getUser(), source.getPassword());
      }

      // Call the specific loading depending of Table or Request.
      loadImpl(connection);
    } catch (SQLException e) {
      throw new AssertJDBException(e);
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          // This exception is mute.
        }
      }
    }
  }

  /**
   * Implementation of the loading that depends of the kind of data.
   * <p>
   * In fact it is like in the Skeleton Design Pattern : this method is called by the {@link AbstractDbDatas#load()}
   * method but {@code loadImpl()} is abstract here and it is implemented in the sub-classes depending of the need of
   * the sub-class.
   * </p>
   * 
   * @see Table#loadImpl(Connection)
   * @see Request#loadImpl(Connection)
   * @param connection {@link Connection} to the database provided by {@link #load()} method.
   * @throws SQLException SQL Exception.
   */
  protected abstract void loadImpl(Connection connection) throws SQLException;

  /**
   * Collects rows from a {@link ResultSet}.
   * <p>
   * This method browse the {@link ResultSet} in parameter to get the data and fill the list of {@link Row} (
   * {@link #rowsList}) with these data.
   * </p>
   * 
   * @param resultSet The {@link ResultSet}.
   * @throws SQLException A SQL Exception.
   */
  protected void collectRowsFromResultSet(ResultSet resultSet) throws SQLException {
    rowsList = new ArrayList<Row>();
    while (resultSet.next()) {
      List<Object> objectsList = new ArrayList<Object>();
      for (String columnName : columnsNameList) {
        objectsList.add(resultSet.getObject(columnName));
      }
      rowsList.add(new Row(columnsNameList, objectsList));
    }
  }

  /**
   * Return the list of the columns name for the data from database.
   * <p>
   * If it is the first call to {@code getColumnsNameList()}, the data are loaded from database by calling the
   * {@link #load()} private method.
   * </p>
   * 
   * @return The list of the columns name.
   * @throws NullPointerException If the {@link #dataSource} and {@link #source} fields are {@code null}.
   * @throws AssertJDBException If triggered, this exception wrap a possible {@link SQLException} during the loading.
   */
  public List<String> getColumnsNameList() {
    if (columnsNameList == null) {
      load();
    }
    return columnsNameList;
  }

  /**
   * Sets the list of the columns name.
   * 
   * @param columnsNameList The list of the columns name.
   */
  protected void setColumnsNameList(List<String> columnsNameList) {
    this.columnsNameList = columnsNameList;
  }

  /**
   * Return the list of the values for the data from database.
   * <p>
   * If it is the first call to {@code getRowsList()}, the data are loaded from database by calling the {@link #load()}
   * private method.
   * </p>
   * 
   * @return The list of the values.
   * @throws NullPointerException If the {@link #dataSource} and {@link #source} fields are {@code null}.
   * @throws AssertJDBException If triggered, this exception wrap a possible {@link SQLException} during the loading.
   */
  public List<Row> getRowsList() {
    if (rowsList == null) {
      load();
    }
    return rowsList;
  }

  /**
   * Returns the column corresponding to the column index in parameter and the values inside the column.
   * <p>
   * This method calls {@link #getColumnsNameList()} and {@link #getValuesList(int)} which calls {@link #getRowsList()}.
   * <br/>
   * If it is the first call to {@link #getColumnsNameList()} or {@link #getRowsList()}, the data are loaded from
   * database by calling the {@link #load()} private method.
   * </p>
   * 
   * @param index The column index.
   * @return The column and the values
   * @throws NullPointerException If the {@link #dataSource} and {@link #source} fields are {@code null}.
   * @throws AssertJDBException If triggered, this exception wrap a possible {@link SQLException} during the loading.
   */
  public Column getColumn(int index) {
    String name = getColumnsNameList().get(index);
    List<Object> valuesList = getValuesList(index);
    return new Column(name, valuesList);
  }

  /**
   * Returns the column corresponding to the column name in parameter and the values inside the column.
   * <p>
   * This method calls {@link #getColumnsNameList()} and {@link #getValuesList(int)} which calls {@link #getRowsList()}.
   * <br/>
   * If it is the first call to {@link #getColumnsNameList()} or {@link #getRowsList()}, the data are loaded from
   * database by calling the {@link #load()} private method.
   * </p>
   * 
   * @param columnName The column name (must be not {@code null}).
   * @return The column and the values
   * @throws NullPointerException If the {@code columnName} parameter is {@code null}.
   * @throws NullPointerException If the {@link #dataSource} and {@link #source} fields are {@code null}.
   * @throws AssertJDBException If triggered, this exception wrap a possible {@link SQLException} during the loading.
   */
  public Column getColumn(String columnName) {
    if (columnName == null) {
      throw new NullPointerException("Column name must be not null");
    }
    String name = columnName.toUpperCase();
    int index = getColumnsNameList().indexOf(name);
    if (index == -1) {
      return null;
    }

    List<Object> valuesList = getValuesList(index);

    return new Column(name, valuesList);
  }

  /**
   * Returns the row corresponding to the index.
   * <p>
   * This method calls {@link #getRowsList()}.<br/>
   * If it is the first call to {@link #getRowsList()}, the data are loaded from database by calling the {@link #load()}
   * private method.
   * </p>
   * 
   * @param index The index
   * @return The {@link Row}
   * @throws NullPointerException If the {@link #dataSource} and {@link #source} fields are {@code null}.
   * @throws AssertJDBException If triggered, this exception wrap a possible {@link SQLException} during the loading.
   */
  public Row getRow(int index) {
    return getRowsList().get(index);
  }

  /**
   * Returns the values of the column corresponding to the column name.
   * <p>
   * This method calls {@link #getColumnsNameList()} and {@link #getRowsList()}.<br/>
   * If it is the first call to {@link #getColumnsNameList()} or {@link #getRowsList()}, the data are loaded from
   * database by calling the {@link #load()} private method.
   * </p>
   * 
   * @param columnName The column name
   * @return The values
   * @throws NullPointerException If the {@link #dataSource} and {@link #source} fields are {@code null}.
   * @throws AssertJDBException If triggered, this exception wrap a possible {@link SQLException} during the loading.
   */
  private List<Object> getValuesList(int index) {
    List<Object> valuesList = new ArrayList<Object>();
    for (Row row : getRowsList()) {
      valuesList.add(row.getColumnValue(index));
    }

    return valuesList;
  }
}