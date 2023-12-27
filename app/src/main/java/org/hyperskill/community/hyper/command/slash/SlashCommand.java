package org.hyperskill.community.hyper.command.slash;

import org.hyperskill.community.hyper.command.Command;

import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public abstract class SlashCommand extends ListenerAdapter implements Command {
    SlashCommandData data;

    public SlashCommand(SlashCommandData data) {
        this.data = data;
    }

    @Override
    public SlashCommandData getData() {
        return data;
    }

    abstract public void onSlashCommand(SlashCommandInteractionEvent event);

    @Override
    public final void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals(getData().getName())) this.onSlashCommand(event);
    }

    // Should not be implemented
    @Override
    public final void onMessageContextInteraction(MessageContextInteractionEvent any) {}

    @Override
    public final void onUserContextInteraction(UserContextInteractionEvent any) {}
}
