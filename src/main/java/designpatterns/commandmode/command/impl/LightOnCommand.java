package designpatterns.commandmode.command.impl;

import designpatterns.commandmode.command.Command;
import designpatterns.commandmode.execute.Light;

/**
 * 灯泡点亮的命令
 */
public class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.on();
    }
}
