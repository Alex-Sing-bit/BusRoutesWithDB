package ru.vsu.cs.baklanova;

import ru.vsu.cs.baklanova.database_interaction.DBType;
import ru.vsu.cs.baklanova.user_interface.UIType;

import java.io.PrintStream;


public class Program {

    public static class CmdParams {
        public boolean window;
        public boolean console;
        public boolean error;
        public boolean help;

        DBType type = DBType.FAKE;

    }

    public static CmdParams parseArgs(String[] args) {
        CmdParams params = new CmdParams();
        if (args.length > 0) {
            if (args[0].equals("--help")) {
                params.help = true;
                return params;
            }
            if (args[0].equals("--window")) {
                params.window = true;
                return params;
            }
            if (args[0].equals("--console")) {
                params.console = true;
            }
            if (args[1].equals("--postgres")) {
                params.type = DBType.POSTGRESQL;
            }
            if (args.length > 3) {
                params.help = true;
                params.error = true;
                return params;
            }

        } else {
            params.help = true;
            params.error = true;
        }
        return params;
    }

    public static void main(String[] args) {
        CmdParams params = parseArgs(args);
        if (params.help) {
            PrintStream out = params.error ? System.err : System.out;
            out.println("Usage:");
            out.println("  <cmd> --console --fake");
            out.println("  <cmd> --window --postgres");
            out.println("  <cmd> --help");
            System.exit(params.error ? 1 : 0);
        }
        Creator creator = new Creator();
        if (params.window) {
            creator.create(params.type, UIType.GUI);
        }
        else if (params.console) {
            creator.create(params.type, UIType.CONSOLE);
        }
        creator.getUserInterface().displayMenu();
    }
}