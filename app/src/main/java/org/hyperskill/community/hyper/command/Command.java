package org.hyperskill.community.hyper.command;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface Command {
    <T extends CommandData> T getData();
}
