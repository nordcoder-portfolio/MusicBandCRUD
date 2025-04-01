package common.util;

import java.io.*;

public class Serializer<T> {
    public byte[] serialize(T toSerialize) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(toSerialize);
        oos.flush();
        return baos.toByteArray();
    }

    public T deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (T) ois.readObject();
    }


}
