package me.retrodaredevil.solarthing.program;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.retrodaredevil.action.node.ActionNode;
import me.retrodaredevil.solarthing.actions.LogActionNode;
import me.retrodaredevil.solarthing.actions.RequireFullOutputActionNode;
import me.retrodaredevil.solarthing.actions.RequiredIdentifierActionNode;
import me.retrodaredevil.solarthing.actions.chatbot.WrappedSlackChatBotActionNode;
import me.retrodaredevil.solarthing.actions.command.WrappedAlterManagerActionNode;
import me.retrodaredevil.solarthing.actions.command.ExecutingCommandFeedbackActionNode;
import me.retrodaredevil.solarthing.actions.command.SendCommandActionNode;
import me.retrodaredevil.solarthing.actions.homeassistant.HomeAssistantActionNode;
import me.retrodaredevil.solarthing.actions.mate.*;
import me.retrodaredevil.solarthing.actions.message.MessageSenderActionNode;
import me.retrodaredevil.solarthing.actions.rover.RoverBoostSetActionNode;
import me.retrodaredevil.solarthing.actions.rover.RoverBoostVoltageActionNode;
import me.retrodaredevil.solarthing.actions.rover.RoverLoadActionNode;
import me.retrodaredevil.solarthing.actions.solcast.SolcastActionNode;
import me.retrodaredevil.solarthing.actions.tracer.TracerLoadActionNode;
import me.retrodaredevil.solarthing.annotations.UtilityClass;
import me.retrodaredevil.solarthing.config.options.CommandOption;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public final class ActionUtil {
	private ActionUtil() { throw new UnsupportedOperationException(); }

	public static ObjectMapper registerActionNodes(ObjectMapper objectMapper) {
		objectMapper.registerSubtypes(
				ActionNode.class,

				LogActionNode.class,

				RequiredIdentifierActionNode.class,
				RequireFullOutputActionNode.class,

				ACModeActionNode.class,
				AuxStateActionNode.class,
				FXOperationalModeActionNode.class,
				MateCommandActionNode.class,
				MateCommandWaitActionNode.class,

				RoverLoadActionNode.class,
				RoverBoostSetActionNode.class,
				RoverBoostVoltageActionNode.class,

				TracerLoadActionNode.class,

				SendCommandActionNode.class,

				HomeAssistantActionNode.class,
				SolcastActionNode.class,

				MessageSenderActionNode.class,

				WrappedSlackChatBotActionNode.class,

				ExecutingCommandFeedbackActionNode.class,

				WrappedAlterManagerActionNode.class
		);
		return objectMapper;
	}

	public static Map<String, ActionNode> getActionNodeMap(ObjectMapper objectMapper, CommandOption options) throws IOException {
		Map<String, ActionNode> actionNodeMap = new HashMap<>();
		for (Map.Entry<String, File> entry : options.getCommandFileMap().entrySet()) {
			String name = entry.getKey();
			File file = entry.getValue();
			final ActionNode actionNode = objectMapper.readValue(file, ActionNode.class);
			actionNodeMap.put(name, actionNode);
		}
		return actionNodeMap;
	}
}
