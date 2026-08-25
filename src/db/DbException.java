package db;

public class DbException extends RuntimeException{
    private static final long serialVersionIUD = 1L;

    public DbException(String msg){
        super(msg);
    }
}
