package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

//we handle all the connection no more then 10.....
public class ConnectionPool {
    private static final int NUM_OF_CONS = DataBaseManager.MAX_CONNECTION;
    private static ConnectionPool instance = null;
    private final Stack<Connection> connections = new Stack<>();

    /**
     * private c'tor to make it singleton.
     * @throws SQLException
     */
    private ConnectionPool() throws SQLException {
        //open all connections
        openAllConnections();
    }

    /**
     * double checks if the instance isn't null, also by locking the entire class after first check.
     * if so, creates a new ConnectionPool and sets it to this instance.
     * @return this instance.
     */
    public static ConnectionPool getInstance() {
        //before locking the critical code...
        if (instance == null) {
            //create the connection pool
            synchronized (ConnectionPool.class) {
                //before creating the code.....
                if (instance == null) {
                    try {
                        instance = new ConnectionPool();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    /**
     * gets an available connection from the stack, awaits if there is no connection available.
     * @return (Connection) a connection from the stack.
     * @throws InterruptedException throws this if theres an exception in the synchronized process.
     */
    public Connection getConnection() throws InterruptedException{
        synchronized (connections){
            if (connections.isEmpty()){
                //wait until we will get a connection back
                connections.wait();
            }
            return connections.pop();
        }
    }

    /**
     * returns a connection to the available connections stack.
     * @param connection the connection to return to the stack.
     */
    public void returnConnection(Connection connection){
        synchronized (connections){
            connections.push(connection);
            //notify that we got back a connection from the user...
            connections.notify();
        }
    }

    /*
        this method will open 10 connection in advanced
        @throws SQLException
     */

    /**
     * Opens connections to the Database, adds all opened connections to the availible connections stack.
     * @throws SQLException error in the connection to the DataBase.
     */
    private void openAllConnections() throws SQLException{
        for (int index=0;index < NUM_OF_CONS;index+=1){
            Connection connection = DriverManager.getConnection(DataBaseManager.URL,DataBaseManager.USER_NAME,DataBaseManager.USER_PASS);
            connections.push(connection);
        }
    }

    /**
     * removes all connections from the stack (closes connection to the DataBase)
     * @throws InterruptedException exception from the synchronized process
     */
    public void closeAllConnection() throws InterruptedException{
        synchronized (connections){
            while (connections.size()<NUM_OF_CONS){
                connections.wait();
            }
            connections.removeAllElements();
        }
    }
}
