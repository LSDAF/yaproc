package com.lsadf.yaproc.command;

import picocli.CommandLine;

@CommandLine.Command(
    name = "properties",
    aliases = {"p", "prop"},
    description = "Converts the given input file to properties")
public class PropertiesCommand extends ACommand implements YaprocCommand {

}
