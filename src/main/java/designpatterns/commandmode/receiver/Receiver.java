package designpatterns.commandmode.receiver;

import designpatterns.commandmode.command.Command;
import designpatterns.commandmode.command.impl.LightOnCommand;
import designpatterns.commandmode.execute.Light;

import java.awt.peer.LightweightPeer;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;


public class Receiver {
    // 命令队列
    private Queue<Command> commands = new ArrayBlockingQueue<>(10);

    public void setCommand(Command command) {
        commands.add(command);
    }

    public void start() {
        while (!commands.isEmpty()) {
            Command command = commands.poll();
            command.execute();
        }
    }

    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Light light = new Light();
        LightOnCommand command = new LightOnCommand(light);
        receiver.setCommand(command);
        receiver.start();
    }
}

