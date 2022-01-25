package exceptions;

public class LoginException extends  Exception{
    public LoginException(){super("Error");}
    //TODO: Should we delete this (use on eerror type)
    public LoginException(String message){super(message);}
}
