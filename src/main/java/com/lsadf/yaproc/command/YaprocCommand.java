package com.lsadf.yaproc.command;

import picocli.CommandLine;

import java.util.concurrent.Callable;

public interface YaprocCommand extends Callable<Integer> {
  CommandLine.Model.CommandSpec getSpec();

  String getInput();

  String getOutput();

  boolean isForce();

  void init();
}
