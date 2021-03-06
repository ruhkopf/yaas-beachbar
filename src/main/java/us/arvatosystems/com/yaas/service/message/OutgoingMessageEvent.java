package us.arvatosystems.com.yaas.service.message;

import org.springframework.context.ApplicationEvent;

import us.arvatosystems.com.yaas.domain.SMSMessage;

public class OutgoingMessageEvent extends ApplicationEvent
{
	private static final long serialVersionUID = -6693096055242104238L;

	private final SMSMessage message;

	public OutgoingMessageEvent(final Object source, final String from, final String to, final String message)
	{
		super(source);

		this.message = new SMSMessage(from, to, message);
	}

	public SMSMessage getMessage()
	{
		return message;
	}

	@Override
	public String toString()
	{
		return "OutgoingMessageEvent [source=" + source + ", message=" + message + "]";
	}

}
