package exceptions;

public class LoginException extends  Exception{
    public LoginException(){super("Error");}

    public LoginException(String message){super(message);}
}
