package Application.interfaces;

import java.util.List;

public interface DaoInterface<T> {
    void update(T nameClass);

    void delete(T nameClass);

    T findId(int id);

    List<T> findAll();
}
