public class IncorrectInputException extends Exception{
    public IncorrectInputException(String errMsg, Throwable tr){
        super(errMsg,tr);
    }

    public IncorrectInputException(String msg){
        super(msg);
    }
}
