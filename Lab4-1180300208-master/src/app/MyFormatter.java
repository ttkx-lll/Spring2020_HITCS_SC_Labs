package app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter {

	private final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@Override
	public String format(LogRecord record) {
		StringBuilder builder = new StringBuilder(1000);
		builder.append(df.format(new Date(record.getMillis()))).append("\n");
		builder.append("[").append(record.getLevel()).append("]\n");
		builder.append("[").append(record.getSourceClassName()).append("]\n");
		builder.append("[").append(record.getSourceMethodName()).append("]\n");
		builder.append("[").append(record.getThrown()).append("]\n");
		builder.append(formatMessage(record));
		builder.append("\n\n");
		return builder.toString();
	}

	@Override
	public String getHead(Handler h) {
		return super.getHead(h);
	}

	@Override
	public String getTail(Handler h) {
		return super.getTail(h);
	}

}
