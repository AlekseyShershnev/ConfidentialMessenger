package confidentialmessenger;

/**
 *
 * @author Shershnev
 */
public class RecordAlreadyExistException extends Exception {

    public RecordAlreadyExistException() {
    }

    public RecordAlreadyExistException(String msg) {
        super(msg);
    }
}
