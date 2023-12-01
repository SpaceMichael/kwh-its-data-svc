package hk.org.ha.kcc.common.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

public class PrefixedSequenceIdGenerator extends SequenceStyleGenerator {

    public static final String PREFIX_PARAM = "prefix";
    public static final String PREFIX_DEFAULT = "";

    public static final String DATE_FORMAT_PARAM = "dateFormat";
    public static final String DATE_FORMAT_DEFAULT = "%ty";

    public static final String NUMBER_FORMAT_PARAM = "numberFormat";
    public static final String NUMBER_FORMAT_DEFAULT = "%07d";

    private String format;

    @Override
    public void configure(Type type, Properties props, ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(LongType.INSTANCE, props, serviceRegistry);
        String prefix = ConfigurationHelper.getString(PREFIX_PARAM, props, PREFIX_DEFAULT);
        String dateFormat = ConfigurationHelper.getString(DATE_FORMAT_PARAM, props, DATE_FORMAT_DEFAULT).replace("%",
                "%1$");
        String numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAM, props, NUMBER_FORMAT_DEFAULT)
                .replace("%", "%2$");
        this.format = prefix + dateFormat + numberFormat;
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return String.format(format, LocalDate.now(), super.generate(session, object));
    }
}
