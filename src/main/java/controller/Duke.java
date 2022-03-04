package controller;

import command.Command;

import java.io.FileNotFoundException;

public class Duke {

    private Storage storage;
    private TaskList tasks;
    private UI ui;


    public Duke(String filePath) {
        ui = new UI();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }


        public void run() {
            ui.showWelcome();
            boolean isExit = false;
            while (!isExit) {
                try {
                    String fullCommand = ui.readCommand();
                    ui.showLine(); // show the divider line ("_______")
                    Command c = Parser.parse(fullCommand);  // make parse as static method !!!
                    c.execute(tasks, ui, storage);
                    isExit = c.isExit();
                } catch (DukeException e) {
                    ui.showError(e.getMessage());
                } finally {
                    ui.showLine();
                }
            }
            return;
        }


    public static void main(String[] args) {
        new Duke("data/task-file").run();
    }
}