package shop.main;

import shop.data.Data;
import shop.data.Inventory;
import shop.data.Video;

/**
 * The States enum implements the State interface.
 * It is used to process input and output and return the next state.
 */
public enum States implements State{
    // The StartState is the initial state of the program.
    StartState { 
        public void process(InputSource inputSource, OutputSource outputSource) {
            outputSource.printMessage("What would you like to do? (Type 'help' for a list of commands)");
            String command = inputSource.validateInput(
                s -> s.equals("help") || s.equals("init") || s.equals("add") || s.equals("remove") 
                || s.equals("check out") || s.equals("check in") || s.equals("clear") 
                || s.equals("undo") || s.equals("redo") || s.equals("list") || s.equals("exit"),
                "", "Invalid command.\n"
            );
            command = command.toLowerCase().trim();
            switch (command){
                case "help":
                    next = HelpState;
                    break;
                case "init":
                    next = InitState;
                    break;
                case "add":
                    next = AddState;
                    break;
                case "remove":
                    next = RemoveState;
                    break;
                case "check out":
                    next = CheckOutState;
                    break;
                case "check in":
                    next = CheckInState;
                    break;
                case "clear":
                    next = ClearState;
                    break;
                case "undo":
                    next = UndoState;
                    break;
                case "redo":
                    next = RedoState;
                    break;
                case "list":
                    next = ListState;
                    break;
                case "exit":
                    next = ExitState;
                    break;
                default:
                    next = StartState;
            }

        }
        public State nextState(){
            return next;
        }
    },
    // The HelpState is used to display a list of commands to the user.
    HelpState {
        public void process(InputSource inputSource, OutputSource outputSource) {
            outputSource.printMessage("Commands: \n"
            + "add: add a video to the inventory\n"
            + "remove: remove a video from the inventory\n"
            + "check out: check out a video\n"
            + "check in: check in a video\n"
            + "clear: clear database (delete all videos)\n"
            + "undo: undo the last command\n"
            + "redo: redo the most recently undone command\n"
            + "list: list all the videos in the inventory\n"
            + "init: initialize the database with bogus content\n"
            + "exit: exit the program\n");
            next = StartState;
        }
        public State nextState(){
            return next;
        };
    }, 
    // The InitState is used to initialize the database with bogus content.
    InitState {
        public void process(InputSource inputSource, OutputSource outputSource) {
            for(int i = 0; i < 10; i++){
                Video video = Data.newVideo("title" + i, 2000 + i, "director" + i);
                Data.newAddCmd(inventory, video, 1).run();
            }
            next = StartState;
        }
        public State nextState(){
            return next;
        };
    },
    // The AddState is used to add a video to the inventory.
    AddState {
        public void process(InputSource inputSource, OutputSource outputSource) {
            String title = inputSource.validateInput(s -> !s.equals(""),"Enter the title of the video: ", "Title cannot be empty.\n");
            int year = Integer.parseInt(inputSource.validateInput(s -> s.matches("\\d+") && Integer.parseInt(s) > 1800 && Integer.parseInt(s) < 5000, 
            "Enter the year of the video: ", "Invalid year.\n"));
            String director = inputSource.validateInput(s -> !s.equals(""),"Enter the director of the video: ", "Director cannot be empty.\n");
            Video video = Data.newVideo(title, year, director);
            int copies = Integer.parseInt(inputSource.validateInput(s -> s.matches("\\d+"), "Enter the number of copies to add: ",
            "Invalid number of copies.\n"));
            if(Data.newAddCmd(inventory, video, copies).run()){
                outputSource.printMessage("Video added to inventory.");
            } else {
                outputSource.printMessage("An error occurred.");
            }
            next = StartState;
        }
        public State nextState(){
            return next;
        };
    },
    // The RemoveState is used to remove a video from the inventory.
    RemoveState {
        public void process(InputSource inputSource, OutputSource outputSource) {
            if(inventory.size() == 0){
                outputSource.printMessage("No videos in the inventory. Please add a video first.");
                next = StartState;
                return;
            } 
            String title = inputSource.validateInput(s -> !s.equals(""),"Enter the title of the video: ", "Title cannot be empty.\n");
            int year = Integer.parseInt(inputSource.validateInput(s -> s.matches("\\d+") && Integer.parseInt(s) > 1800 && Integer.parseInt(s) < 5000, 
            "Enter the year of the video: ", "Invalid year.\n"));
            String director = inputSource.validateInput(s -> !s.equals(""),"Enter the director of the video: ", "Director cannot be empty.\n");
            Video video = Data.newVideo(title, year, director);
            int copies = Integer.parseInt(inputSource.validateInput(s -> s.matches("\\d+"), "Enter the number of copies to add: ",
            "Invalid number of copies.\n"));
            if(Data.newAddCmd(inventory, video, -copies).run()){
                outputSource.printMessage("Video removed from inventory.");
            } else {
                outputSource.printMessage("Video not in inventory.");
            }
            next = StartState;
        }
        public State nextState(){
            return next;
        };
    },
    // The CheckOutState is used to check out a video.
    CheckOutState {
        public void process(InputSource inputSource, OutputSource outputSource) {
            if(inventory.size() == 0){
                outputSource.printMessage("No videos in the inventory. Please add a video first.");
                next = StartState;
                return;
            }
            String title = inputSource.validateInput(s -> !s.equals(""),"Enter the title of the video: ", "Title cannot be empty.\n");
            int year = Integer.parseInt(inputSource.validateInput(s -> s.matches("\\d+") && Integer.parseInt(s) > 1800 && Integer.parseInt(s) < 5000, 
            "Enter the year of the video: ", "Invalid year.\n"));
            String director = inputSource.validateInput(s -> !s.equals(""),"Enter the director of the video: ", "Director cannot be empty.\n");
            Video video = Data.newVideo(title, year, director);
            if(inventory.get(video) == null){
                outputSource.printMessage("Video not in inventory.");
                next = StartState;
                return;
            }
            else if (inventory.get(video).numOut() == inventory.get(video).numOwned()){
                outputSource.printMessage("All copies of the video are checked out.");
                next = StartState;
                return;
            }
            if(Data.newOutCmd(inventory, video).run()){
                outputSource.printMessage("Video checked out.");
            } else {
                outputSource.printMessage("An error occurred.");
            }
            next = StartState;
        }
        public State nextState(){
            return next;
        };
    },
    // The CheckInState is used to check in a video.
    CheckInState {
        public void process(InputSource inputSource, OutputSource outputSource) {
            if(inventory.size() == 0){
                outputSource.printMessage("No videos in the inventory. Please add a video first.");
                next = StartState;
                return;
            }
            String title = inputSource.validateInput(s -> !s.equals(""),"Enter the title of the video: ", "Title cannot be empty.\n");
            int year = Integer.parseInt(inputSource.validateInput(s -> s.matches("\\d+") && Integer.parseInt(s) > 1800 && Integer.parseInt(s) < 5000, 
            "Enter the year of the video: ", "Invalid year.\n"));
            String director = inputSource.validateInput(s -> !s.equals(""),"Enter the director of the video: ", "Director cannot be empty.\n");
            Video video = Data.newVideo(title, year, director);
            if(inventory.get(video) == null){
                outputSource.printMessage("Video not in inventory.");
                next = StartState;
                return;
            }
            else if (inventory.get(video).numOut() == 0){
                outputSource.printMessage("No copies checked out.");
                next = StartState;
                return;
            }
            if(Data.newInCmd(inventory, video).run()){
                outputSource.printMessage("Video checked in.");
            } else {
                outputSource.printMessage("An error occurred.");
            }
            next = StartState;
        }
        public State nextState(){
            return next;
        };
    },
    // The ClearState is used to clear the database.
    ClearState {
        public void process(InputSource inputSource, OutputSource outputSource) {
            Data.newClearCmd(inventory).run();
            next = StartState;
        }
        public State nextState(){
            return next;
        };
    },
    // The UndoState is used to undo the last command.
    UndoState {
        public void process(InputSource inputSource, OutputSource outputSource) {
            Data.newUndoCmd(inventory).run();
            next = StartState;
        }
        public State nextState(){
            return next;
        };
    },
    // The RedoState is used to redo the most recently undone command.
    RedoState {
        public void process(InputSource inputSource, OutputSource outputSource) {
            Data.newRedoCmd(inventory).run();
            next = StartState;
        }
        public State nextState(){
            return next;
        };
    },
    // The ListState is used to list all the videos in the inventory.
    ListState {
        public void process(InputSource inputSource, OutputSource outputSource) {
            outputSource.printMessage(inventory.toString());
            next = StartState;
        }
        public State nextState(){
            return next;
        };
    },
    // The ExitState is used to exit the program.
    ExitState {
        public void process(InputSource inputSource, OutputSource outputSource) {
            System.exit(0);
        }
        public State nextState(){
            return null;
        };
    };

    // The next state to transition to.
    private static State next;
    // The inventory of videos.
    private static Inventory inventory = Data.newInventory();
    
    // The process method processes the input and output.
    public abstract void process(InputSource inputSource, OutputSource outputSource);
    // The nextState method returns the next state.
    public abstract State nextState();
}