public class WordAlreadyExistsException extends Exception{
    public WordAlreadyExistsException(){
        super("Already exists");
    }
    public WordAlreadyExistsException(String s){
        super(s+"Already exists");
    }

    
}
