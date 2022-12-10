package gameClasses;

/**
 * Interface for executing commands with multiple arguments in the game.
 */
public interface Command {
    /**
     * Executes the command.
     * @param commands The command and its arguments.
     */
    public void execCommand(String[] commands);
}
