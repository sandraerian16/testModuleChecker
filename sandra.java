package de.allianz.abs.junit.test.config; 
import java.util.Arrays; import java.util.Collections;
import java.util.List; 
import java.util.Properties; 
import de.allianz.abs.adag.base.config.logic.ConfigLocation; 
import de.allianz.abs.adag.base.config.logic.Environment; 
import de.allianz.abs.adag.base.config.logic.FileConfigLocation; 
import de.allianz.abs.adag.base.config.logic.IConfigLocationFinder;
import de.allianz.abs.adag.base.config.logic.InstallMode; 
import de.allianz.abs.adag.base.config.logic.ResourceConfigLocation;
public class TestConfigLocationFinder implements IConfigLocationFinder {
    @Override 
    public void setProperties(Properties props) 
    { 
        String res = System.getProperty("resdir"); 
        if (res != null && res.length() > 0) {
            props.put("resdir", res);
        } 
            //Setzen über System-Property auch möglich => Umgebung/Testuser über VM-Variable austauschbar 
            props.put("DBNAME", System.getProperty("DBNAME", "AZD-ATE0")); 
            props.put("TESTUSER", System.getProperty("TESTUSER", "AXHABKL"));
            props.put("APPLICATION", "ABS"); // hard coded because of problems while loading the plugins 
    } 
    @Override
    public List<ConfigLocation> getConfigLocations()
    { 
        if (Environment.getApplicationContext().getInstallMode() == InstallMode.UNITTEST) 
        { 
            //@formatter:off 
            return Arrays.asList( new ResourceConfigLocation(getClass().getClassLoader(), "/config.data"),
                new FileConfigLocation("C:/DevADAG/ABSHead/unittest-config.data", true) );
                //@formatter:on 
        }        
        System.err.println("WARNING: JUnit configuration plugin in " + Environment.getApplicationContext().getInstallMode()
            + " configuration"); return Collections.emptyList(); 
    } 
}
