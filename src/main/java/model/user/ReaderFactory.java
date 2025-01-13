package model.user;

public class ReaderFactory {
    public static Reader createReader(String username, String password, String name) {
        Reader reader = new Reader();
        reader.setUsername(username);
        reader.setPassword(password);
        reader.setName(name);
        return reader;
    }
}
