package org.hyperskill.community.hyper;

import java.util.List;

import org.hyperskill.community.hyper.command.Command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.hyperskill.community.hyper.command.slash.PingCommand;

public final class App {
    public static void main(String[] args) {
        JDA jda = JDABuilder.createDefault(args[0]).build();

        List<? extends Command> commands = List.of(
            new PingCommand()
        );
        commands.forEach(jda::addEventListener);

        List<? extends CommandData> commandsData = commands.stream()
            .map(Command::<CommandData>getData)
            .toList();
        jda.updateCommands().addCommands(commandsData).queue();
    }
}
