package pl.bTech.carsappII.utilities.entity;

import com.google.gson.*;
import pl.bTech.carsappII.utilities.HibernateProxyTypeAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
public abstract class SerializableEntityAbstract implements SerializableEntityInterface {


    protected abstract String[] getFieldsToSkip();

    public String toJson() {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                                            public boolean shouldSkipClass(Class<?> clazz) {
                                                return false; //TODO mock
                                            }

                                            public boolean shouldSkipField(FieldAttributes f) {
                                                List<String> fieldsToSkip = Arrays.asList(getFieldsToSkip());

                                                if (fieldsToSkip.contains(f.getName())) {
                                                    return true;
                                                }
                                                return false;
                                            }
                                        }
                ).registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY)
                .serializeNulls()
                .create();
        return gson.toJson(this);
    }

    public boolean fromJson(String jsonString) {
        Field[] fields = this.getClass().getDeclaredFields();
        JsonObject jsonObject = (JsonObject) (new JsonParser()).parse(jsonString);
        try {
            for (Field field : fields) {
                String fieldName = field.getName();
                String setterName = String.valueOf(
                        new StringBuilder()
                                .append("set").append(fieldName.substring(0, 1).toUpperCase())
                                .append(fieldName.substring(1))
                );

                if (jsonObject.get(fieldName) == null) {
                    continue;
                }

                if (field.getType().isAssignableFrom(List.class)) {
                    continue;
                }

                if (field.getType().isAssignableFrom(String.class)) {
                    if (jsonObject.get(fieldName).isJsonPrimitive()) {
                        String stringProperty = jsonObject.get(fieldName).getAsString();
                        this.invoke(setterName, field.getType(), stringProperty);
                    }
                    continue;
                }

                Type[] primitiveTypes = {
                        Integer.TYPE,
                        Byte.TYPE,
                        Character.TYPE,
                        Long.TYPE,
                        Boolean.TYPE,
                        Short.TYPE,
                        Double.TYPE,
                        Float.TYPE
                };

                List<String> types = new ArrayList<>();
                for (Type s : primitiveTypes) {
                    types.add(s.getTypeName());
                }

                if (jsonObject.get(fieldName).isJsonPrimitive() && types.contains(field.getType().getName())) {
                    for (Type type : primitiveTypes) {
                        String typeName = type.getTypeName();
                        if (parsePrimitiveType(jsonObject, field, fieldName, setterName, typeName)) {
                            continue;
                        }
                    }
                    continue;
                }

                Object object = null;
                try {
                    object = field.getType().newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                    continue;
                }

                if (SerializableEntityInterface.class.isAssignableFrom(object.getClass())) {
                    SerializableEntityInterface serializableEntity = (SerializableEntityInterface) object;

                    if (jsonObject.get(fieldName).isJsonPrimitive()) {
                        serializableEntity.setId(jsonObject.get(fieldName).getAsInt());
                    }

                    if (jsonObject.get(fieldName).isJsonObject()) {
                        serializableEntity.fromJson(jsonObject.get(fieldName).getAsJsonObject().toString());
                    }

                    this.invoke(setterName, field.getType(), object);
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
        ;
        return true;
    }

    private boolean parsePrimitiveType(JsonObject jsonObject, Field field, String fieldName, String setterName, String typeName) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (field.getType().isPrimitive() && field.getType().getName().equalsIgnoreCase(typeName)) {
            if (jsonObject.get(fieldName).isJsonPrimitive()) {

                Object property = this.extractPrimitiveValueFromJsonElement(jsonObject.get(fieldName), typeName);
                if (property == null) {
                    return false;
                }
                this.invoke(setterName, field.getType(), property);
            }
            return true;
        }
        return false;
    }

    private Object extractPrimitiveValueFromJsonElement(JsonElement jsonElement, String typeName) {
        if (typeName.equalsIgnoreCase(Integer.TYPE.getName())) {
            return jsonElement.getAsInt();
        }
        if (typeName.equalsIgnoreCase(Byte.TYPE.getName())) {
            return jsonElement.getAsByte();
        }
        if (typeName.equalsIgnoreCase(Character.TYPE.getName())) {
            return jsonElement.getAsCharacter();
        }
        if (typeName.equalsIgnoreCase(Long.TYPE.getName())) {
            return jsonElement.getAsLong();
        }
        if (typeName.equalsIgnoreCase(Boolean.TYPE.getName())) {
            return jsonElement.getAsBoolean();
        }
        if (typeName.equalsIgnoreCase(Short.TYPE.getName())) {
            return jsonElement.getAsShort();
        }
        if (typeName.equalsIgnoreCase(Double.TYPE.getName())) {
            return jsonElement.getAsDouble();
        }
        if (typeName.equalsIgnoreCase(Float.TYPE.getName())) {
            return jsonElement.getAsFloat();
        }

        return null;
    }

    private void invoke(String setterName, Type type, Object parameter) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = this.getClass().getMethod(setterName, (Class<?>) type);
        method.setAccessible(true);
        method.invoke(this, parameter);
    }
}
