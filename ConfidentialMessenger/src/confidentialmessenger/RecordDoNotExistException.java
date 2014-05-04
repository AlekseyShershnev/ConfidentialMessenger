package confidentialmessenger;

/**
 *
 * @author Shershnev
 */
public class RecordDoNotExistException extends Exception {

    public RecordDoNotExistException() {
    }

    public RecordDoNotExistException(String msg) {
        super(msg);
    }
}