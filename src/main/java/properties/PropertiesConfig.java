package properties; /**
 * Created by vasilev on 28/03/2019.
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesConfig {
    private static final Logger LOGGER = LogManager.getLogger(PropertiesConfig.class.getName());

    private static final Properties PROPERTIES = getProperties();

    private PropertiesConfig()
    {}

    private static Properties getProperties()
    {
        final Properties properties = new Properties();
        final ClassLoader classLoader = PropertiesConfig.class.getClassLoader();
        final String file = "application-test.properties";

        try
        {
            final Enumeration<URL> resources = classLoader.getResources(file);
            while (resources.hasMoreElements())
            {
                final  URL currentResource = resources.nextElement();
                try(InputStream stream = currentResource.openStream()) {
                    properties.load(stream);
                }
            }
        }
        catch (IOException e) {
            LOGGER.error("ERROR while reading property file: {}");
            throw new RuntimeException(e);
        }
        return properties;
    }

    public static String getProperty(String propertyName)
    {
        final String result = PROPERTIES.getProperty(propertyName);
        Assert.assertNotNull("Property must not be null " + propertyName, result);

        return result;
    }
}
