package com.splitwise.commands;

import com.splitwise.exceptions.BadCommandFormatException;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory implements Command {
    Map<String, Command> commandMap;

    private static CommandFactory Instance;

    private CommandFactory() {
        commandMap = new HashMap<>();
        commandMap.put("add_user", new AddUserCommand());
        commandMap.put("add_expense", new AddExpenseCommand());
        commandMap.put("add_split", new AddSplitCommand());
        commandMap.put("show", new ShowCommand());
    }

    public static CommandFactory getInstance() {
        if(Instance == null)
            Instance = new CommandFactory();
        return Instance;
    }

    @Override
    public void execute(String[] cmd) throws BadCommandFormatException {
        if(!commandMap.containsKey(cmd[0])) {
            throw new BadCommandFormatException("Unrecognized command " + cmd[0]);
        }
        commandMap.get(cmd[0]).execute(cmd);
    }
}
