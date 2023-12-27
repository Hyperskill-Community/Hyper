package org.hyperskill.community.hyper.command.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public final class PingCommand extends SlashCommand {
    public PingCommand() {
        super(Commands.slash("ping", "Replies with Pong!"));
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event) {
        event.reply("Pong!").queue();
    }
}
