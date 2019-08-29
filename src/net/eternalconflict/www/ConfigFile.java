package net.eternalconflict.www;

import net.eternalconflict.www.enums.subtypes.PlanetTypeEnum;
import net.eternalconflict.www.enums.subtypes.StarTypeEnum;
import net.eternalconflict.www.holders.CoordinatesHolder;
import net.eternalconflict.www.holders.objects.DefaultObject;
import net.eternalconflict.www.holders.objects.PlanetObject;
import net.eternalconflict.www.holders.objects.StarObject;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class ConfigFile {
    private Properties prop = new Properties();
    private File configfile;
    public ConfigFile()
    {
        configfile = null;
    }
    public ConfigFile(String folder, String fileName)
    {
        this.setFile(folder, fileName);
        loadFromFile();
    }
    public ConfigFile clone()
    {
        ConfigFile configFile = new ConfigFile();
        configFile.loadFromString(this.saveToString());
        return configFile;
    }
    public void setFile(String folder, String fileName)
    {
        File directory = new File(EternalConflict.mainPath,  "saves");
        if (!(directory.exists())) directory.mkdir();
        directory = new File(directory,   folder);
        if (!(directory.exists())) directory.mkdir();
        configfile = new File(directory,   fileName);
    }
    public void loadFromFile()
    {
        if (configfile != null) {
            if (configfile.exists()) {
                InputStream is = null;
                try {
                    is = new FileInputStream(configfile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    prop.load(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static String decode(String encoded)
    {
        return new String(DatatypeConverter.parseBase64Binary(encoded), StandardCharsets.UTF_8);
    }
    public static String encode(String text)
    {
        return DatatypeConverter.printBase64Binary(text.getBytes(StandardCharsets.UTF_8));
    }
    private static Properties parsePropertiesString(String s) {
        try {
            final Properties p = new Properties();
            p.load(new StringReader(s));
            return p;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static String getPropertyAsString(Properties prop) {
        StringWriter writer = new StringWriter();
        try {
            prop.store(writer, "");
        } catch (IOException e) {

        }
        return writer.getBuffer().toString();
        /*
        StringWriter writer = new StringWriter();
        prop.list(new PrintWriter(writer));
        return writer.getBuffer().toString();*/
    }

    public boolean containsKey(String key)
    {
        return prop.containsKey(key);
    }
    public void set(String key, Object value)
    {
        if (value instanceof Boolean)
        {
            prop.setProperty(key, ((Boolean) value).toString());
        }
        else if (value instanceof Long)
        {
            prop.setProperty(key, Long.toString((Long) value));
        }
        else if (value instanceof Integer)
        {
            prop.setProperty(key, Integer.toString((Integer) value));
        }
        else if (value instanceof Double)
        {
            prop.setProperty(key, Double.toString((Double) value));
        }
        else if (value instanceof List)
        {
            List<Object> temp = (List<Object>) value;
            if (temp.size() > 0) {
                if (temp.get(0) instanceof DefaultObject) {
                    ConfigFile cf = new ConfigFile();
                    cf.set("i.size", temp.size());
                    cf.set("i.type", "DefaultObject");
                    for(int i = 0; i<temp.size();i++)
                    {
                        cf.set("i." + i, (DefaultObject)temp.get(i));
                    }
                    String saving = DatatypeConverter.printBase64Binary(cf.saveToString().getBytes(StandardCharsets.UTF_8));
                    prop.setProperty(key, saving);

                } else if (temp.get(0) instanceof String) {
                    ConfigFile cf = new ConfigFile();
                    cf.set("i.size", temp.size());
                    cf.set("i.type", "String");
                    for(int i = 0; i<temp.size();i++)
                    {
                        cf.set("i." + i, (String)temp.get(i));
                    }
                    String saving = DatatypeConverter.printBase64Binary(cf.saveToString().getBytes(StandardCharsets.UTF_8));
                    prop.setProperty(key, saving);
                } else if (temp.get(0) instanceof Double) {
                    ConfigFile cf = new ConfigFile();
                    cf.set("i.size", temp.size());
                    cf.set("i.type", "Double");
                    for(int i = 0; i<temp.size();i++)
                    {
                        cf.set("i." + i, (Double)temp.get(i));
                    }
                    String saving = DatatypeConverter.printBase64Binary(cf.saveToString().getBytes(StandardCharsets.UTF_8));
                    prop.setProperty(key, saving);
                } else if (temp.get(0) instanceof CoordinatesHolder) {
                    ConfigFile cf = new ConfigFile();
                    cf.set("i.size", temp.size());
                    cf.set("i.type", "CoordinatesHolder");
                    for(int i = 0; i<temp.size();i++)
                    {
                        cf.set("i." + i, (CoordinatesHolder)temp.get(i));
                    }
                    String saving = DatatypeConverter.printBase64Binary(cf.saveToString().getBytes(StandardCharsets.UTF_8));
                    prop.setProperty(key, saving);
                } else if (temp.get(0) instanceof Integer) {
                    ConfigFile cf = new ConfigFile();
                    cf.set("i.size", temp.size());
                    cf.set("i.type", "Integer");
                    for(int i = 0; i<temp.size();i++)
                    {
                        cf.set("i." + i, (Integer)temp.get(i));
                    }
                    String saving = DatatypeConverter.printBase64Binary(cf.saveToString().getBytes(StandardCharsets.UTF_8));
                    prop.setProperty(key, saving);
                }
            }
        }
        else if (value instanceof PlanetTypeEnum)
        {
            prop.setProperty(key, Integer.toString(((PlanetTypeEnum)value).getID()));
        }
        else if (value instanceof StarTypeEnum)
        {
            prop.setProperty(key, Integer.toString(((StarTypeEnum)value).getID()));
        }
        else if (value instanceof CoordinatesHolder)
        {
            ConfigFile cf = new ConfigFile();
            cf.set("i.x", ((CoordinatesHolder)value).getX());
            cf.set("i.z", ((CoordinatesHolder)value).getZ());
            String saving = DatatypeConverter.printBase64Binary(cf.saveToString().getBytes(StandardCharsets.UTF_8));
            prop.setProperty(key, saving);
        }
        else if (value instanceof DefaultObject)
        {
            if (value instanceof StarObject)
            {
                StarObject starObject = (StarObject) value;
                ConfigFile cf = new ConfigFile();
                cf.set("i.header", "star");
                cf.set("i.c", starObject.getPosition());
                cf.set("i.size", starObject.getSize());
                cf.set("i.id", starObject.getId());
                cf.set("i.name", starObject.getName());
                cf.set("i.type", starObject.getStarType());
                String saving = DatatypeConverter.printBase64Binary(cf.saveToString().getBytes(StandardCharsets.UTF_8));
                prop.setProperty(key, saving);
            }
            else if (value instanceof PlanetObject)
            {
                PlanetObject planetObject = (PlanetObject) value;
                ConfigFile cf = new ConfigFile();
                cf.set("i.header", "planet");
                cf.set("i.c", planetObject.getPosition());
                cf.set("i.size", planetObject.getSize());
                cf.set("i.id", planetObject.getId());
                cf.set("i.name", planetObject.getName());
                cf.set("i.type", planetObject.getPlanetType());
                String saving = DatatypeConverter.printBase64Binary(cf.saveToString().getBytes(StandardCharsets.UTF_8));
                prop.setProperty(key, saving);
            }
            else
            {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("Saving unknow object type");
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");
                DefaultObject defaultObject = (DefaultObject) value;
                ConfigFile cf = new ConfigFile();
                cf.set("i.header", "?????");
                cf.set("i.c", defaultObject.getPosition());
                cf.set("i.id", defaultObject.getId());
                cf.set("i.name", defaultObject.getName());
                String saving = DatatypeConverter.printBase64Binary(cf.saveToString().getBytes(StandardCharsets.UTF_8));
                prop.setProperty(key, saving);
            }
        }
        else if (value instanceof String) {
            prop.setProperty(key, (String) value);
        }
    }
    public Set<String> getKeys()
    {
        return prop.stringPropertyNames();
    }
    public String saveToString()
    {
        return ConfigFile.getPropertyAsString(prop);
    }
    public DefaultObject getDefaultObject(String key)
    {
        if (prop.getProperty(key) != null) {
            ConfigFile configFile = new ConfigFile();
            configFile.loadFromString(new String(DatatypeConverter.parseBase64Binary(prop.getProperty(key)), StandardCharsets.UTF_8));

            if (!configFile.containsKey("i.header")) return null;
            String header = configFile.getString("i.header");
            CoordinatesHolder c;
            double size;
            String id;
            String name;

            switch (header.toLowerCase()) {
                case "star":
                    c = configFile.getCoordinates("i.c");
                    size = configFile.getDouble("i.size");
                    id = configFile.getString("i.id");
                    name = configFile.getString("i.name");
                    StarTypeEnum starTypeEnum = configFile.getStarType("i.type");
                    StarObject starObject = new StarObject(id, name, c);
                    starObject.setStarType(starTypeEnum);
                    starObject.setSize(size);
                    return starObject;
                case "planet":
                    c = configFile.getCoordinates("i.c");;
                    size = configFile.getDouble("i.size");
                    id = configFile.getString("i.id");
                    name = configFile.getString("i.name");
                    PlanetTypeEnum planetTypeEnum = configFile.getPlanetType("i.type");
                    PlanetObject planetObject = new PlanetObject(id, name, c);
                    planetObject.setPlanetType(planetTypeEnum);
                    planetObject.setSize(size);
                    return planetObject;
                default:
                    c = configFile.getCoordinates("i.c");
                    id = configFile.getString("i.id");
                    name = configFile.getString("i.name");
                    DefaultObject defaultObject = new DefaultObject(id, name, c);
                    return defaultObject;
            }
        }
        return null;
    }
    public void loadFromString(String info) {
        prop = ConfigFile.parsePropertiesString(info);
    }
    public StarTypeEnum getStarType(String key)
    {
        if (prop.getProperty(key) != null) {
            int i = Integer.parseInt(prop.getProperty(key));
            return StarTypeEnum.valueOf(i);
        }
        return null;
    }
    public PlanetTypeEnum getPlanetType(String key)
    {
        if (prop.getProperty(key) != null) {
            int i = Integer.parseInt(prop.getProperty(key));
            return PlanetTypeEnum.valueOf(i);
        }
        return null;
    }
    public List<DefaultObject> getDefaultObjectList(String key)
    {
        if (prop.getProperty(key) != null) {
            ConfigFile configFile = new ConfigFile();
            configFile.loadFromString(new String(DatatypeConverter.parseBase64Binary(prop.getProperty(key)), StandardCharsets.UTF_8));
            int size = configFile.getInteger("i.size");
            String type = configFile.getString("i.type");
            List<DefaultObject> temp = new ArrayList<DefaultObject>();
            if (type.equals("DefaultObject")) {
                for (int i = 0; i < size; i++) {
                    DefaultObject defaultObject = configFile.getDefaultObject("i." + i);
                    temp.add(defaultObject);
                }
                return temp;
            }
        }
        return null;
    }
    public List<Double> getDoubleList(String key)
    {
        if (prop.getProperty(key) != null) {
            ConfigFile configFile = new ConfigFile();
            configFile.loadFromString(new String(DatatypeConverter.parseBase64Binary(prop.getProperty(key)), StandardCharsets.UTF_8));
            int size = configFile.getInteger("i.size");
            String type = configFile.getString("i.type");
            List<Double> temp = new ArrayList<Double>();
            if (type.equals("Double")) {
                for (int i = 0; i < size; i++) {
                    Double defaultObject = configFile.getDouble("i." + i);
                    temp.add(defaultObject);
                }
                return temp;
            }
        }
        return null;
    }
    public List<String> getStringList(String key)
    {
        if (prop.getProperty(key) != null) {
            ConfigFile configFile = new ConfigFile();
            configFile.loadFromString(new String(DatatypeConverter.parseBase64Binary(prop.getProperty(key)), StandardCharsets.UTF_8));
            int size = configFile.getInteger("i.size");
            String type = configFile.getString("i.type");
            List<String> temp = new ArrayList<String>();
            if (type.equals("String")) {
                for (int i = 0; i < size; i++) {
                    String defaultObject = configFile.getString("i." + i);
                    temp.add(defaultObject);
                }
                return temp;
            }
        }
        return null;
    }
    public List<Integer> getIntegerList(String key)
    {
        if (prop.getProperty(key) != null) {
            ConfigFile configFile = new ConfigFile();
            configFile.loadFromString(new String(DatatypeConverter.parseBase64Binary(prop.getProperty(key)), StandardCharsets.UTF_8));
            int size = configFile.getInteger("i.size");
            String type = configFile.getString("i.type");
            List<Integer> temp = new ArrayList<Integer>();
            if (type.equals("Integer")) {
                for (int i = 0; i < size; i++) {
                    Integer defaultObject = configFile.getInteger("i." + i);
                    temp.add(defaultObject);
                }
                return temp;
            }
        }
        return null;
    }
    public List<CoordinatesHolder> getCoordinateList(String key)
    {
        if (prop.getProperty(key) != null) {
            ConfigFile configFile = new ConfigFile();
            configFile.loadFromString(new String(DatatypeConverter.parseBase64Binary(prop.getProperty(key)), StandardCharsets.UTF_8));
            int size = configFile.getInteger("i.size");
            String type = configFile.getString("i.type");
            List<CoordinatesHolder> temp = new ArrayList<CoordinatesHolder>();
            if (type.equals("CoordinatesHolder")) {
                for (int i = 0; i < size; i++) {
                    CoordinatesHolder defaultObject = configFile.getCoordinates("i." + i);
                    temp.add(defaultObject);
                }
                return temp;
            }
        }
        return null;
    }
    public Long getLong(String key)
    {
        try {
            Long x = Long.parseLong(prop.getProperty(key));
            return x;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Integer getInteger(String key)
    {
        try {
            Integer x = Integer.parseInt(prop.getProperty(key));
            return x;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Double getDouble(String key)
    {
        try {
            Double x = Double.parseDouble(prop.getProperty(key));
            return x;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
    public CoordinatesHolder getCoordinates(String key)
    {
        if (prop.getProperty(key) != null) {
            ConfigFile configFile = new ConfigFile();
            configFile.loadFromString(new String(DatatypeConverter.parseBase64Binary(prop.getProperty(key)), StandardCharsets.UTF_8));
            Double x = configFile.getDouble("i.x");
            Double z = configFile.getDouble("i.z");
            CoordinatesHolder coordinatesHolder = new CoordinatesHolder(x, z);
            return coordinatesHolder;
        }
        return null;
    }
    public String getString(String key)
    {
        String s = prop.getProperty(key);
        return s;
    }
    public Boolean getBoolean(String key)
    {
        try {
            String s = prop.getProperty(key);
            Boolean out = Boolean.parseBoolean(s);
            return out;
        } catch (Exception e) {
            return null;
        }
    }
    public void save()
    {
        try {
            if (!configfile.exists()) configfile.createNewFile();
            OutputStream out = new FileOutputStream( configfile );
            prop.store(out, null);
        }
        catch (Exception e ) {
            e.printStackTrace();
        }
    }
}
