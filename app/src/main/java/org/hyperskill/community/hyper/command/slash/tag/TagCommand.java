package org.hyperskill.community.hyper.command.slash.tag;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.hyperskill.community.hyper.command.slash.SlashCommand;


public class TagCommand extends SlashCommand {
    public TagCommand() {

        super(Commands
                .slash("tag", "bot message")

                .addOptions(
                        new OptionData(OptionType.STRING, "id", "id of tag command")
                                .addChoice(TagMessageConstants.HOW_TO_ASK_ID, TagMessageConstants.HOW_TO_ASK_VALUE)
                                .addChoice(TagMessageConstants.HOW_TO_CONTACT_SUPPORT_ID, TagMessageConstants.HOW_TO_CONTACT_SUPPORT_VALUE)
                )
        );
    }

    /**
     * send pre-defined bot messages to user
     *
     * @param event interaction event
     */
    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event) {
        event.reply(getTagOption(event.getCommandString())).queue();
    }

    /**
     *
     * parse command string from event and extract message value
     *
     * @param commandString full command that includes id and value
     *
     * @return value
     */
    private String getTagOption(String commandString){

        // command string is : /tag id: <value>
        String[] commandSegments = commandString.split(":");
        return commandSegments[1].trim();
    }
}
