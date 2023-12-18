package globalwaves.commands;

import fileio.input.ActionInput;

public interface Command {
    /**
     * Executes the command with the provided action input.
     *
     * @param action The action input containing information necessary for executing the command.
     */
    void execute(ActionInput action);
}
