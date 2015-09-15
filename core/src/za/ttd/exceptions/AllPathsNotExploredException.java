package za.ttd.exceptions;

/**
 * @author minnaar
 * @since 2015/09/15.
 */
public class AllPathsNotExploredException extends IllegalStateException {
    public AllPathsNotExploredException() {}
    public AllPathsNotExploredException(String message) {
        super(message);
    }
}
