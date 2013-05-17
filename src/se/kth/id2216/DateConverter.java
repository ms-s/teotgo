package se.kth.id2216;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
public class DateConverter implements Converter{
	public boolean canConvert( @SuppressWarnings("rawtypes") Class clazz ) {
		return Calendar.class.isAssignableFrom( clazz );
	}
	public void marshal( Object value, HierarchicalStreamWriter writer,
            MarshallingContext context ) {
        Calendar calendar = (Calendar) value;
        Date date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        writer.setValue(formatter.format(date));
	}
	public Object unmarshal( HierarchicalStreamReader reader,
            UnmarshallingContext context ) {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        try {
                calendar.setTime( formatter.parse( reader.getValue() ) );
        } catch ( ParseException e ) {
                throw new ConversionException( e.getMessage(), e );
        }
        return calendar;
	}
}